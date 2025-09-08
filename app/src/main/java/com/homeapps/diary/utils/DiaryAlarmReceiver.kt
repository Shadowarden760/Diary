package com.homeapps.diary.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.homeapps.diary.data.jobs.DiaryAlarmScheduler
import com.homeapps.diary.domain.api.AlarmRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DiaryAlarmReceiver: BroadcastReceiver(), KoinComponent {
    private val alarmRepository: AlarmRepository by inject()
        private val alarmScheduler: DiaryAlarmScheduler by inject()

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(DiaryAlarmReceiver::class.java.name, intent.action ?: "")
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                val intent = Intent(context, DiaryAlarmReceiver::class.java)
                alarmRepository.getAllAlarms().forEach { alarmItem ->
                    Log.d(DiaryAlarmReceiver::class.java.name, "$alarmItem")
                    alarmScheduler.alarmSchedule(intent, alarmItem)
                }
            }

        } else {
            val notificationManager = DiaryNotificationManager(context)
            notificationManager.createNotificationChannel()
            notificationManager.showNotification()
        }
    }
}