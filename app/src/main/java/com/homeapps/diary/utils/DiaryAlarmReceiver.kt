package com.homeapps.diary.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class DiaryAlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notificationManager = DiaryNotificationManager(it)
            notificationManager.createNotificationChannel()
            notificationManager.showNotification()
        }
    }
}