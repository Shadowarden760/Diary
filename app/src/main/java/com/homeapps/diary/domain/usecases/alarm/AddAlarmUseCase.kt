package com.homeapps.diary.domain.usecases.alarm

import android.content.Intent
import com.homeapps.diary.domain.api.AlarmRepository
import com.homeapps.diary.domain.api.AlarmScheduler

class AddAlarmUseCase(
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler,
) {

    suspend operator fun invoke(intent: Intent, timeMillis: Long): Boolean {
        val alarmItem = alarmRepository.addAlarm(timeMillis)
        return if (alarmItem != null) {
            alarmScheduler.alarmSchedule(intent, alarmItem)
        } else {
            false
        }
    }
}