package com.homeapps.diary.domain.api

import android.content.Intent
import com.homeapps.diary.domain.models.alarm.AlarmItem

interface AlarmScheduler {

    fun alarmSchedule(intent: Intent, alarmItem: AlarmItem): Boolean

    fun alarmCancel(intent: Intent, alarmItem: AlarmItem): Boolean
}