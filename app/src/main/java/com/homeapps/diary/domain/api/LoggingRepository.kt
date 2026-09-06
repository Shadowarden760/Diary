package com.homeapps.diary.domain.api

import com.homeapps.diary.LogMessageDBO

interface LoggingRepository {

    enum class LogLevel(val level: String) {
        INFO(level = "INFO"),
        WARNING(level = "WARNING"),
        ERROR(level = "ERROR")
    }

    suspend fun getAllLogMessages(): List<LogMessageDBO>

    suspend fun createLogMessage(logMessageLevel: LogLevel, logMessageText: String)

    suspend fun deleteOldLogMessages(thresholdTimeMillis: Long)
}

