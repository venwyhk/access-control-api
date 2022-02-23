package com.github.leon.template.task.devops

import com.github.leon.generator.entity.Task
import com.github.leon.generator.entity.TaskOfProject

class NginxTask : Task(
        replaceFile = true,
        taskOfProject = TaskOfProject.API,
        name = "Nginx",
        folder = """ "/nginx" """,
        taskType = "single",
        filename = """ project.name + "-all" """,
        templatePath = """ "devops/nginx.ftl" """
)