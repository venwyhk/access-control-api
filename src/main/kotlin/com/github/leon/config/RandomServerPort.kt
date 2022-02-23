package com.github.leon.config

import org.apache.commons.lang3.RandomUtils

class RandomServerPort {

    private var serverPort: Int = 0

    private val start = 0
    private val end = 65535

    fun nextValue(start: Int, end: Int ): Int {
        var start = start
        var end = end
        start = if (start < this.start) this.start else start
        end = if (end > this.end) this.end else end

        if (serverPort == 0) {
            synchronized(this) {
                if (serverPort == 0) {
                    serverPort = RandomUtils.nextInt(start, end)
                }
            }
        }
        return serverPort
    }
}

