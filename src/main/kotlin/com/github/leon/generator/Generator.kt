package com.github.leon.generator

import com.github.leon.generator.entity.*
import com.github.leon.generator.ext.Utils
import com.github.leon.generator.script.DefaultScriptHelper
import com.github.leon.generator.template.FreeMarkerHelper
import com.github.leon.template.apiTasks
import com.github.leon.template.uiTasks
import java.util.*

fun generate(codeEnv: CodeEnv? = null): List<Pair<Task, List<String>>> {
    val appProps = Properties()
    appProps.load(Thread.currentThread().contextClassLoader.getResourceAsStream("generator/local.properties"))
    val projectName = appProps.getProperty("projectName")
    val packageName = appProps.getProperty("packageName")
    val domain = appProps.getProperty("domain")
    val ip = appProps.getProperty("ip")
    val gitHost = appProps.getProperty("gitHost")
    val entityPackageName = appProps.getProperty("entityLocationPattern")
    val apiTargetPath = System.getProperty("user.dir")

    val uiTargetPath: String
    val testTargetPath: String
    val uiTemplateTargetPath: String

    if (codeEnv == null) {
        uiTargetPath = appProps.getProperty("uiTargetPath")
        testTargetPath = appProps.getProperty("testTargetPath")
        uiTemplateTargetPath = appProps.getProperty("uiTemplateTargetPath")
    } else {
        uiTargetPath = codeEnv.uiTargetPath
        testTargetPath = codeEnv.testTargetPath
        uiTemplateTargetPath = codeEnv.uiTemplateTargetPath
    }

    val entities = scanForCodeEntities(entityPackageName)
    entities.forEach {
        println(it.name)
        println(it.fields.map { it.name }.toList())
    }
    val enums = scanForCodeEnum()
    return CodeProject(
            name = projectName,
            entities = entities,
            enums = enums,
            utilClasses = listOf(Utils::class.java),
            packageName = packageName,
            domain = domain,
            ip = ip,
            gitHost = gitHost,
            uiTemplateTargetPath = uiTemplateTargetPath,
            testTasks = listOf(),
            uiTasks = uiTasks(),
            apiTasks = apiTasks(),
            uiTargetPath = uiTargetPath,
            apiTargetPath = apiTargetPath,
            testTargetPath = testTargetPath,
            scriptHelper = DefaultScriptHelper("groovy"),
            templateEngine = FreeMarkerHelper()
    ).generate(codeEnv)
}


