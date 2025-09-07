package com.homeapps.diary.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.homeapps.diary.data.jobs.DiaryAlarmScheduler


class DiaryAlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(DiaryAlarmReceiver::class.java.name, intent.action ?: "")
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
//            val alarmScheduler = DiaryAlarmScheduler(context)
//            val intent = Intent(context, DiaryAlarmReceiver::class.java)
//            alarmScheduler.alarmSchedule(intent = intent, timeMillis = System.currentTimeMillis())
        } else {
            val notificationManager = DiaryNotificationManager(context)
            notificationManager.createNotificationChannel()
            notificationManager.showNotification()
        }
    }
}