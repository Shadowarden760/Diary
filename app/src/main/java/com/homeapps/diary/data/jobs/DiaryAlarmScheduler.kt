package com.homeapps.diary.data.jobs

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.homeapps.diary.domain.api.AlarmScheduler
import com.homeapps.diary.utils.DiaryAlarmReceiver

class DiaryAlarmScheduler(private val appContext: Context): AlarmScheduler {
    private val alarmManager = appContext.getSystemService(AlarmManager::class.java)

    override fun alarmSchedule(intent: Intent, timeMillis: Long): Boolean {
        return try {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                timeMillis,
                AlarmManager.INTERVAL_DAY,
                PendingIntent.getBroadcast(
                    appContext,
                    ALARM_ID,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            isAlarmSet()
        } catch (ex: SecurityException) {
            Log.e(ex.message, "Alarm Id = $ALARM_ID")
            false
        } catch (ex: Exception) {
            Log.e(ex.message, "Alarm Id = $ALARM_ID")
            false
        }
    }

    override fun alarmCancel(intent: Intent): Boolean {
        val pendingIntent = PendingIntent.getBroadcast(
            appContext,
            ALARM_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
        return !isAlarmSet()
    }

    fun isAlarmSet(): Boolean {
        val intent = Intent(appContext, DiaryAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            appContext,
            ALARM_ID,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        return pendingIntent != null
    }

    companion object {
        private const val ALARM_ID = 1001
    }
}