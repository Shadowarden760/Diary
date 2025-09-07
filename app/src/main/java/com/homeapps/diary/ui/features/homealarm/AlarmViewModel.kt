package com.homeapps.diary.ui.features.homealarm

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.homeapps.diary.domain.usecases.alarm.CancelAlarmUseCase
import com.homeapps.diary.domain.usecases.alarm.SetAlarmUseCase
import com.homeapps.diary.utils.DiaryAlarmReceiver

class AlarmViewModel(
    private val appContext: Context,
    private val setAlarmUseCase: SetAlarmUseCase,
    private val cancelAlarmUseCase: CancelAlarmUseCase,
): ViewModel() {

    fun cancelAlarmScheduler(): Boolean {
        val intent = Intent(appContext, DiaryAlarmReceiver::class.java)
        return cancelAlarmUseCase(intent = intent)
    }

    fun setAlarmScheduler(timeMillis: Long): Boolean {
        val intent = Intent(appContext, DiaryAlarmReceiver::class.java)
        return setAlarmUseCase(intent = intent, timeMillis = timeMillis)
    }
}