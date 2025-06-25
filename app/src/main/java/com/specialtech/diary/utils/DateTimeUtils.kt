package com.specialtech.diary.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object DateTimeUtils {

    fun formatDate(
        dateString: String,
        dateFormat: String = "yyyy-MM-dd HH:mm",
        returnFormat: String = "dd-MM-yyyy HH:mm"
    ): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        val returnFormatter = SimpleDateFormat(returnFormat, Locale.getDefault())
        try {
            val date = formatter.parse(dateString)
            return if (date != null) returnFormatter.format(date) else ""
        } catch (_: Exception) {
            return ""
        }
    }

    fun timeMillisToDate(
        timeMillis: Long,
        format: String = "dd-MM-yyyy HH:mm"
    ): String {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        try {
            val date = formatter.format(Date(timeMillis))
            return date
        } catch (_: Exception) {
            return ""
        }
    }

}