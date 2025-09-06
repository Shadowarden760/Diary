package com.homeapps.diary.domain.usecases.alarm

import android.content.Intent
import com.homeapps.diary.data.jobs.DiaryAlarmScheduler

class SetAlarmUseCase(private val alarmScheduler: DiaryAlarmScheduler) {

    operator fun invoke(intent: Intent, timeMillis: Long): Boolean {
        return alarmScheduler.alarmSchedule(intent, timeMillis)
    }
}