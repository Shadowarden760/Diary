package com.homeapps.diary.domain.api

import com.homeapps.diary.LogMessageDBO

interface LoggingRepository {
    val logLevel: LogLevel

    enum class LogLevel(val level: String, val weight: Int) {
        INFO(level = "INFO", weight =  3),
        WARNING(level = "WARNING", weight = 2),
        ERROR(level = "ERROR", weight = 1)
    }

    suspend fun getAllLogMessages(): List<LogMessageDBO>

    suspend fun createLogMessage(logMessageLevel: LogLevel, logMessageText: String)

    suspend fun deleteOldLogMessages(thresholdTimeMillis: Long)
}

