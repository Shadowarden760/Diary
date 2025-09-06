package com.homeapps.diary.domain.api

import android.content.Intent

interface AlarmScheduler {

    fun alarmSchedule(intent: Intent, timeMillis: Long): Boolean

    fun alarmCancel(intent: Intent): Boolean
}