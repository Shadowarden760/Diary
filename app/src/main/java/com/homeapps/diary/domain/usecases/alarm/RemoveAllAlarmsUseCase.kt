package com.homeapps.diary.domain.usecases.alarm

import android.content.Intent
import com.homeapps.diary.domain.api.AlarmRepository
import com.homeapps.diary.domain.api.AlarmScheduler

class RemoveAllAlarmsUseCase(
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler,
) {

    suspend operator fun invoke(intent: Intent): Boolean {
        val alarmCancelStatus = mutableListOf<Boolean>()
        val alarms = alarmRepository.getAllAlarms()
        alarms.forEach { alarmItem ->
            alarmCancelStatus.add(alarmScheduler.alarmCancel(intent, alarmItem))
        }
        val numberOfDeletedAlarms = alarmRepository.removeAllAlarms()
        return alarmCancelStatus.all { it } && numberOfDeletedAlarms.toInt() == alarmCancelStatus.size
    }
}