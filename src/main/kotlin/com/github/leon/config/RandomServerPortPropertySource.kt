package com.github.leon.config

import org.springframework.core.env.PropertySource
import org.springframework.util.StringUtils

class RandomServerPortPropertySource : PropertySource<RandomServerPort> {

    @JvmOverloads constructor(name: String = RANDOM_SERVER_PORT_PROPERTY_SOURCE_NAME) : super(name, RandomServerPort())

    constructor(name: String, source: RandomServerPort) : super(name, source) {}

    override fun getProperty(name: String): Any? {
        return if (!name.startsWith(PREFIX)) {
            null
        } else getRandomServerPortValue(name.substring(PREFIX.length))

    }

    private fun getRandomServerPortValue(type: String): Any? {
        val range = getRange(type, "value")
        return if (range != null) {
            getNextValueInRange(range)
        } else null
    }

    private fun getRange(type: String, prefix: String): String? {
        if (type.startsWith(prefix)) {
            val startIndex = prefix.length + 1
            if (type.length > startIndex) {
                return type.substring(startIndex, type.length - 1)
            }
        }
        return null
    }

    private fun getNextValueInRange(range: String): Int {
        val tokens = StringUtils.commaDelimitedListToStringArray(range)
        val start = Integer.parseInt(tokens[0])
        return getSource().nextValue(start, Integer.parseInt(tokens[1]))
    }

    companion object {
        const val RANDOM_SERVER_PORT_PROPERTY_SOURCE_NAME = "randomServerPort"
        private const val PREFIX = "randomServerPort."
    }
}
