package com.homeapps.diary.domain.usecases.alarm

import android.content.Intent
import com.homeapps.diary.data.jobs.DiaryAlarmScheduler

class CancelAlarmUseCase(private val alarmScheduler: DiaryAlarmScheduler) {

    operator fun invoke(intent: Intent): Boolean {
        return alarmScheduler.alarmCancel(intent)
    }
}