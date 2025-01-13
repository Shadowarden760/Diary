package com.specialtech.diary.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


object DateTimeUtils {
    private const val DATETIME_TAG = "Date time Utils Log ->"

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
            Log.e(DATETIME_TAG, "${ex.message}")
            return ""
        }
    }

    fun getYesterdayDateString(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        return dateFormat.format(calendar.time)
    }

}