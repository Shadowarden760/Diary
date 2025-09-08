package com.homeapps.diary.data.datasources.alarms

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOne
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.homeapps.diary.AlarmDBO
import com.homeapps.diary.DiaryDB
import com.homeapps.diary.data.clients.DatabaseDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class AlarmsDatabaseDao(databaseDriver: DatabaseDriver) {
    private val database = DiaryDB.Companion(databaseDriver.createDatabaseDriver())
    private val queries = database.alarmDBOQueries

    suspend fun insertAlarm(timeMillis: Long): AlarmDBO? {
        queries.insertAlarm(
            alarmTimeMillis = timeMillis,
            alarnCreatedAt = System.currentTimeMillis()
        )
        val alarmId = queries.lastInsertedAlarmId().awaitAsOne()
        return queries.getAlarmById(alarmId = alarmId).awaitAsOneOrNull()
    }

    suspend fun getAllAlarms(): List<AlarmDBO> {
        return queries.getAllAlarms().awaitAsList()
    }

    fun getAllAlarmsFlow(): Flow<List<AlarmDBO>> {
        return queries.getAllAlarms()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    suspend fun getAlarmById(alarmId: Long): AlarmDBO? {
        return queries.getAlarmById(alarmId = alarmId).awaitAsOneOrNull()
    }

    suspend fun updateAlarmById(alarmId: Long, newTime: Long): AlarmDBO? {
        return queries.getAlarmById(alarmId).awaitAsOneOrNull()?.let {
            queries.updateAlarm(
                alarmId = it.alarmId,
                alarmTimeMillis = newTime,
                alarnCreatedAt = it.alarnCreatedAt
            )
            queries.getAlarmById(alarmId).executeAsOneOrNull()
        }
    }

    suspend fun deleteAlarmById(alarmId: Long): Long {
        return queries.deleteAlarmById(alarmId = alarmId)
    }

    suspend fun deleteAllAlarms(): Long {
        return queries.deleteAllAlarms()
    }
}