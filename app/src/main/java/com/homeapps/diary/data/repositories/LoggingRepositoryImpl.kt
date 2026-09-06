package com.homeapps.diary.data.repositories

import com.homeapps.diary.LogMessageDBO
import com.homeapps.diary.data.datasources.logging.LogDatabaseDao
import com.homeapps.diary.domain.api.LoggingRepository

class LoggingRepositoryImpl(private val dao: LogDatabaseDao): LoggingRepository {

    override suspend fun getAllLogMessages(): List<LogMessageDBO> {
        return dao.getAllLogMessages()
    }

    override suspend fun createLogMessage(
        logMessageLevel: LoggingRepository.LogLevel,
        logMessageText: String
    ) {
        dao.createNewLogMessage(logMessageLevel = logMessageLevel, logMessageText = logMessageText)
    }

    override suspend fun deleteOldLogMessages(thresholdTimeMillis: Long) {
        dao.deleteOldLogMessages(thresholdTimeMillis = thresholdTimeMillis)
    }
}