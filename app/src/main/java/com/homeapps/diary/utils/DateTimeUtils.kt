package com.homeapps.diary.utils

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
        return try {
            val date = formatter.parse(dateString)
            if (date != null) returnFormatter.format(date) else ""
        } catch (_: Exception) {
            ""
        }
    }

    fun timeMillisToDate(timeMillis: Long, format: String = "dd-MM-yyyy HH:mm"): String {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return try {
            formatter.format(Date(timeMillis))
        } catch (_: Exception) {
            ""
        }
    }
}