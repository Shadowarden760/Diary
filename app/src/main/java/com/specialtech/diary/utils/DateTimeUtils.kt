package com.specialtech.diary.utils

import java.text.SimpleDateFormat
import java.util.Locale


object DateTimeUtils {

    fun formatDate(
        dateString: String,
        dateFormat: String = "yyyy-MM-dd HH:mm",
        returnFormat: String = "dd-MM-yyyy HH:mm"): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        val returnFormatter = SimpleDateFormat(returnFormat, Locale.getDefault())
        try {
            val date = formatter.parse(dateString)
            return if (date != null) returnFormatter.format(date) else ""
        } catch (ex: Exception) {
            return ""
        }
    }

}