package com.github.leon.generator.task.processor

import arrow.core.toOption
import com.github.leon.extentions.println
import com.github.leon.generator.entity.CodeProject
import com.github.leon.generator.entity.Task
import com.github.leon.generator.task.service.TaskService
import com.google.common.collect.Lists
import org.apache.commons.beanutils.PropertyUtils

class MultipleTaskProcessor : ITaskProcessor {
    override fun run(codeProject: CodeProject, task: Task, context: MutableMap<String, Any>): List<String> {
        val paths = Lists.newArrayList<String>()
        for (codeEntity in codeProject.entities) {
            val codeEntityMap = PropertyUtils.describe(codeEntity)
            task.entityExtProcessor.toOption().forEach {
                codeEntityMap.putAll(it.invoke(codeEntity))
            }
            task.templateHelper!!.put("entity", codeEntityMap)
            context["entity"] = codeEntityMap

            paths.add(TaskService.processTemplate(codeProject, task, context))
        }
        return paths
    }
}
