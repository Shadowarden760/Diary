package com.homeapps.diary.domain.usecases.alarm

import android.content.Intent
import com.homeapps.diary.data.jobs.DiaryAlarmScheduler
import com.homeapps.diary.domain.api.AlarmRepository

class AddAlarmUseCase(
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: DiaryAlarmScheduler,
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