package com.homeapps.diary.ui.features.homealarm

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.domain.models.alarm.AlarmItem
import com.homeapps.diary.domain.usecases.alarm.RemoveAllAlarmsUseCase
import com.homeapps.diary.domain.usecases.alarm.AddAlarmUseCase
import com.homeapps.diary.domain.usecases.alarm.GetAllAlarmsUseCase
import com.homeapps.diary.domain.usecases.alarm.RemoveAlarmUseCase
import com.homeapps.diary.utils.DiaryAlarmReceiver
import kotlinx.coroutines.launch

class AlarmViewModel(
    private val appContext: Context,
    private val addAlarmUseCase: AddAlarmUseCase,
    private val removeAlarmUseCase: RemoveAlarmUseCase,
    private val removeAllAlarmsUseCase: RemoveAllAlarmsUseCase,
    getAllAlarmUseCase: GetAllAlarmsUseCase,
): ViewModel() {
    val alarms = getAllAlarmUseCase()

    fun addNewAlarm(timeMillis: Long) = viewModelScope.launch {
        val intent = Intent(appContext, DiaryAlarmReceiver::class.java)
        addAlarmUseCase(intent = intent, timeMillis = timeMillis)
    }

    fun removeAlarm(alarmItem: AlarmItem) = viewModelScope.launch {
        val intent = Intent(appContext, DiaryAlarmReceiver::class.java)
        removeAlarmUseCase(intent = intent, alarmItem = alarmItem)
    }

    fun removeAllAlarms() = viewModelScope.launch {
        val intent = Intent(appContext, DiaryAlarmReceiver::class.java)
        removeAllAlarmsUseCase(intent = intent)
    }
}