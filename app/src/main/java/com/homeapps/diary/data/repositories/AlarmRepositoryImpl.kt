package com.homeapps.diary.data.repositories

import com.homeapps.diary.data.datasources.alarms.AlarmsDatabaseDao
import com.homeapps.diary.data.mappers.toAlarmItem
import com.homeapps.diary.domain.api.AlarmRepository
import com.homeapps.diary.domain.models.alarm.AlarmItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlarmRepositoryImpl(private val dao: AlarmsDatabaseDao): AlarmRepository {

    override suspend fun addAlarm(timeMillis: Long): AlarmItem? {
        return dao.insertAlarm(timeMillis = timeMillis)?.toAlarmItem()
    }

    override suspend fun getAllAlarms(): List<AlarmItem> {
        return dao.getAllAlarms().map { it.toAlarmItem() }
    }

    override fun getAllAlarmsFlow(): Flow<List<AlarmItem>> {
        return dao.getAllAlarmsFlow().map { it.map { alarmDBO -> alarmDBO.toAlarmItem() } }
    }

    override suspend fun removeAlarm(alarmId: Long): Boolean {
        return dao.deleteAlarmById(alarmId = alarmId) > 0
    }

    override suspend fun removeAllAlarms(): Long {
        return dao.deleteAllAlarms()
    }
}