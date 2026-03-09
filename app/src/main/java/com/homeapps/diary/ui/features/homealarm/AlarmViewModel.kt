package com.homeapps.diary.ui.features.homealarm

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.domain.models.alarm.AlarmItem
import com.homeapps.diary.domain.usecases.alarm.AddAlarmUseCase
import com.homeapps.diary.domain.usecases.alarm.GetAllAlarmsUseCase
import com.homeapps.diary.domain.usecases.alarm.RemoveAlarmUseCase
import com.homeapps.diary.domain.usecases.alarm.RemoveAllAlarmsUseCase
import com.homeapps.diary.utils.DiaryAlarmReceiver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlarmViewModel(
    private val appContext: Context,
    private val addAlarmUseCase: AddAlarmUseCase,
    private val removeAlarmUseCase: RemoveAlarmUseCase,
    private val removeAllAlarmsUseCase: RemoveAllAlarmsUseCase,
    getAllAlarmUseCase: GetAllAlarmsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
): ViewModel() {
    val state: StateFlow<AlarmScreenState>
        field = MutableStateFlow<AlarmScreenState>(AlarmScreenState.Default)
    val alarms = getAllAlarmUseCase()

    fun resetState() {
        state.value = AlarmScreenState.Default
    }

    fun addNewAlarm(timeMillis: Long) = viewModelScope.launch {
        val intent = Intent(appContext, DiaryAlarmReceiver::class.java)
        val result = withContext(dispatcher) {
            addAlarmUseCase(intent = intent, timeMillis = timeMillis)
        }
        state.value = AlarmScreenState.AddNewAlarm(result)
    }

    fun removeAlarm(alarmItem: AlarmItem) = viewModelScope.launch {
        val intent = Intent(appContext, DiaryAlarmReceiver::class.java)
        val result = withContext(dispatcher) {
            removeAlarmUseCase(intent = intent, alarmItem = alarmItem)
        }
        state.value = AlarmScreenState.RemoveAlarm(result)
    }

    fun removeAllAlarms() = viewModelScope.launch {
        val intent = Intent(appContext, DiaryAlarmReceiver::class.java)
        val result = withContext(dispatcher) {
            removeAllAlarmsUseCase(intent = intent)
        }
        state.value = AlarmScreenState.RemoveAllAlarms(result)
    }

    sealed class AlarmScreenState {
        object Default: AlarmScreenState()
        data class AddNewAlarm(val status: Boolean): AlarmScreenState()
        data class RemoveAlarm(val status: Boolean): AlarmScreenState()
        data class RemoveAllAlarms(val status: Boolean): AlarmScreenState()
    }
}