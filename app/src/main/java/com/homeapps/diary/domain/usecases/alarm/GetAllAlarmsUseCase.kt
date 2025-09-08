package com.homeapps.diary.domain.usecases.alarm

import com.homeapps.diary.domain.api.AlarmRepository
import com.homeapps.diary.domain.models.alarm.AlarmItem
import kotlinx.coroutines.flow.Flow

class GetAllAlarmsUseCase(private val alarmRepository: AlarmRepository) {

    operator fun invoke(): Flow<List<AlarmItem>> {
        return alarmRepository.getAllAlarmsFlow()
    }
}