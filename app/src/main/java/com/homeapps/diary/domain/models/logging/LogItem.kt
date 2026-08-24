package com.homeapps.diary.domain.models.logging

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LogItem(
    val logLevel: String,
    val logMessage: String,
    val logMessageCreatedAt: Long
) {

    override fun toString(): String {
        val format = "dd-MM-yyyy HH:mm:ss"
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        val date = runCatching {
            formatter.format(Date(logMessageCreatedAt))
        }.getOrDefault("")
        return "[$date]  $logLevel  $logMessage"
    }
}