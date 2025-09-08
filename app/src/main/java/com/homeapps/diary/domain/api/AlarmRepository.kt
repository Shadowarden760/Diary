package com.homeapps.diary.domain.api

import com.homeapps.diary.domain.models.alarm.AlarmItem
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    suspend fun addAlarm(timeMillis: Long): AlarmItem?

    suspend fun getAllAlarms(): List<AlarmItem>

    fun getAllAlarmsFlow(): Flow<List<AlarmItem>>

    suspend fun removeAlarm(alarmId: Long): Boolean

    suspend fun removeAllAlarms(): Long
}