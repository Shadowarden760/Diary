package com.homeapps.diary.domain.usecases.alarm

import android.content.Intent
import com.homeapps.diary.domain.api.AlarmRepository
import com.homeapps.diary.domain.api.AlarmScheduler
import com.homeapps.diary.domain.models.alarm.AlarmItem

class RemoveAlarmUseCase(
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler,
) {

    suspend operator fun invoke(intent: Intent, alarmItem: AlarmItem): Boolean {
        val cancelStatus = alarmScheduler.alarmCancel(intent, alarmItem)
        val removeStatus = alarmRepository.removeAlarm(alarmItem.alarmId)
        return cancelStatus && removeStatus
    }
}