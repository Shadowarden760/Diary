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
        return runCatching {
            val date = formatter.parse(dateString)
            if (date != null) returnFormatter.format(date) else ""
        }.getOrDefault("")
    }

    fun timeMillisToDate(timeMillis: Long, format: String = "dd-MM-yyyy HH:mm"): String {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return runCatching {
            formatter.format(Date(timeMillis))
        }.getOrDefault("")
    }
}