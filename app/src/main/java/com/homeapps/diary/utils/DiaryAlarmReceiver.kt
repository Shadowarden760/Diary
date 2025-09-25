package com.homeapps.diary.utils

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.homeapps.diary.domain.api.AlarmRepository
import com.homeapps.diary.domain.api.AlarmScheduler
import com.homeapps.diary.domain.models.alarm.AlarmItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.max


class DiaryAlarmReceiver: BroadcastReceiver(), KoinComponent {
    private val alarmRepository: AlarmRepository by inject()
    private val alarmScheduler: AlarmScheduler by inject()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                alarmRepository.getAllAlarms().forEach { alarmItem ->
                    schedulerNextAlarm(context = context, alarmItem = alarmItem)
                }
            }
        } else {
            val alarmItemId = intent.getLongExtra("ALARM_ID", -1L)
            if (alarmItemId != -1L) {
                CoroutineScope(Dispatchers.IO).launch {
                    alarmRepository.getAlarmById(alarmItemId)?.let {
                        schedulerNextAlarm(context = context, alarmItem = it)
                    }
                }
                val notificationManager = DiaryNotificationManager(context)
                notificationManager.createNotificationChannel()
                notificationManager.showNotification()
            }
        }
    }

    private suspend fun schedulerNextAlarm(context: Context, alarmItem: AlarmItem) {
        val intent = Intent(context, DiaryAlarmReceiver::class.java)
        val missedDays = max(0, System.currentTimeMillis() - alarmItem.alarmTimeMillis) / AlarmManager.INTERVAL_DAY
        val nextTriggerTime = alarmItem.alarmTimeMillis + AlarmManager.INTERVAL_DAY * (missedDays + 1)
        alarmRepository.updateAlarm(alarmId = alarmItem.alarmId, newTime = nextTriggerTime)?.let {
            alarmScheduler.alarmSchedule(intent = intent, alarmItem = it)
        }
    }
}