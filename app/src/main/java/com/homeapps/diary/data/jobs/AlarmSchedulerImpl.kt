package com.homeapps.diary.data.jobs

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.homeapps.diary.domain.api.AlarmScheduler
import com.homeapps.diary.domain.models.alarm.AlarmItem
import com.homeapps.diary.utils.DiaryAlarmReceiver

class AlarmSchedulerImpl(private val appContext: Context): AlarmScheduler {
    private val alarmManager = appContext.getSystemService(AlarmManager::class.java)

    override fun alarmSchedule(intent: Intent, alarmItem: AlarmItem): Boolean {
        return try {
            intent.putExtra("ALARM_ID", alarmItem.alarmId)
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmItem.alarmTimeMillis,
                PendingIntent.getBroadcast(
                    appContext,
                    alarmItem.alarmId.toInt(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            isAlarmSet(alarmItem)
        } catch (ex: SecurityException) {
            Log.e(AlarmSchedulerImpl::class.java.name, "${ex.message}")
            false
        } catch (ex: Exception) {
            Log.e(AlarmSchedulerImpl::class.java.name, "${ex.message}")
            false
        }
    }

    override fun alarmCancel(intent: Intent, alarmItem: AlarmItem): Boolean {
        intent.putExtra("ALARM_ID", alarmItem.alarmId)
        val pendingIntent = PendingIntent.getBroadcast(
            appContext,
            alarmItem.alarmId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
        return !isAlarmSet(alarmItem)
    }

    fun isAlarmSet(alarmItem: AlarmItem): Boolean {
        val intent = Intent(appContext, DiaryAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            appContext,
            alarmItem.alarmId.toInt(),
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        return pendingIntent != null
    }
}