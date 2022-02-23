package com.github.leon.template.task.devops

import com.github.leon.generator.entity.Task
import com.github.leon.generator.entity.TaskOfProject

class NginxHttpsTask : Task(
        replaceFile = true,
        taskOfProject = TaskOfProject.API,
        name = "Nginx",
        folder = """ "/nginx" """,
        taskType = "single",
        filename = """ project.name + "-https-all" """,
        templatePath = """ "devops/nginxHttps.ftl" """
)