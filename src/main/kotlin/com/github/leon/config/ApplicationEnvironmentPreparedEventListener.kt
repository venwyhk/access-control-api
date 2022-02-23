package com.github.leon.config

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.context.ApplicationListener

class ApplicationEnvironmentPreparedEventListener : ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    override fun onApplicationEvent(event: ApplicationEnvironmentPreparedEvent) {
        event.environment.propertySources.addLast(RandomServerPortPropertySource())
    }
}

