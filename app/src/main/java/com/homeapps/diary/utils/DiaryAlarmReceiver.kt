package com.homeapps.diary.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.homeapps.diary.data.jobs.DiaryAlarmScheduler


class DiaryAlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notificationManager = DiaryNotificationManager(it)
            notificationManager.createNotificationChannel()
            notificationManager.showNotification()
            val diaryAlarmScheduler = DiaryAlarmScheduler(it)
            val intent = Intent(it, DiaryAlarmReceiver::class.java)
            diaryAlarmScheduler.alarmSchedule(intent, System.currentTimeMillis())
        }
    }
}