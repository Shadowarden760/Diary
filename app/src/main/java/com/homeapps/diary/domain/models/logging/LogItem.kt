package com.homeapps.diary.domain.models.logging

import com.homeapps.diary.utils.DateTimeUtils.timeMillisToDate

class LogItem(
    val logLevel: String,
    val logMessage: String,
    val logMessageCreatedAt: Long
) {

    override fun toString(): String {
        val date = timeMillisToDate(timeMillis = logMessageCreatedAt, format = "dd-MM-yyyy HH:mm:ss")
        return "[$date]  $logLevel  $logMessage"
    }
}