package com.github.leon.template.task.devops

import com.github.leon.generator.entity.Task
import com.github.leon.generator.entity.TaskOfProject
import com.github.leon.template.TaskConstants

class JenkinsTask : Task(
        replaceFile = true,
        taskOfProject = TaskOfProject.API,
        name = "Jenkins",
        folder = """ "/" """,
        taskType = "single",
        filename = """ "Jenkinsfile" """,
        templatePath = """ "devops/Jenkinsfile.ftl" """
)