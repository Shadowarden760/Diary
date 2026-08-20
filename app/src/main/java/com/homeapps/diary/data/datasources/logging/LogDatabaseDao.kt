package com.homeapps.diary.data.datasources.logging

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOne
import com.homeapps.diary.DiaryDB
import com.homeapps.diary.LogMessageDBO
import com.homeapps.diary.data.clients.DatabaseDriver
import com.homeapps.diary.domain.api.LoggingRepository

class LogDatabaseDao(databaseDriver: DatabaseDriver) {
    private val database = DiaryDB.Companion(databaseDriver.createDatabaseDriver())
    private val queries = database.logDBOQueries

    suspend fun createNewLogMessage(
        logMessageLevel: LoggingRepository.LogLevel,
        logMessageText: String
    ): Long {
        queries.insertNewLogMessage(
            logMessageLevel = logMessageLevel.level,
            logMessageText = logMessageText,
            logMessageCreatedAt = System.currentTimeMillis()
        )
        return queries.lastInsertedLogMessageId().awaitAsOne()
    }

    suspend fun getAllLogMessages(): List<LogMessageDBO> {
        return queries.getAllLogMessages().awaitAsList()
    }

    suspend fun deleteOldLogMessages(thresholdTimeMillis: Long) {
        queries.deleteOldLogMessages(logMessageCreatedAt = thresholdTimeMillis)
    }
}