package com.github.leon.template.task.devops

import com.github.leon.generator.entity.Task
import com.github.leon.generator.entity.TaskOfProject
import com.github.leon.template.TaskConstants

class RancherTask : Task(
        replaceFile = false,
        taskOfProject = TaskOfProject.API,
        name = "Rancher",
        folder = """ "/rancher/" """,
        taskType = "single",
        filename = """ "develop" """,
        templatePath = """ "devops/rancher.ftl" """
)