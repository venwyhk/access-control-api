package com.github.leon.template.task.devops

import com.github.leon.generator.entity.Task
import com.github.leon.generator.entity.TaskOfProject
import com.github.leon.template.TaskConstants

class HttpsTask : Task(
        replaceFile = false,
        taskOfProject = TaskOfProject.API,
        name = "Rancher",
        folder = """ "/https/" """,
        taskType = "single",
        filename = """ "letsencrypt.sh" """,
        templatePath = """ "devops/https.ftl" """
)