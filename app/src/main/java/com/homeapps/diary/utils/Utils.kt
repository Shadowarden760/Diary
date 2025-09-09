package com.homeapps.diary.utils

import android.annotation.SuppressLint

object Utils {

    @SuppressLint("DefaultLocale")
    fun formatNumberToString(value: Number, pattern: String = "%02d"): String {
        return String.format(pattern, value)
    }
}