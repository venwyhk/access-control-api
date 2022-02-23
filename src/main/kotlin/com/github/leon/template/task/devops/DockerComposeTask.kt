package com.github.leon.template.task.devops

import com.github.leon.generator.entity.Task
import com.github.leon.generator.entity.TaskOfProject
import com.github.leon.template.TaskConstants

class DockerComposeTask : Task(
        replaceFile = true,
        taskOfProject = TaskOfProject.API,
        name = "DockerCompose",
        folder = """ "/docker/" """,
        taskType = "single",
        filename = """ "docker-compose.yml" """,
        templatePath = """ "devops/docker-compose.ftl" """
)