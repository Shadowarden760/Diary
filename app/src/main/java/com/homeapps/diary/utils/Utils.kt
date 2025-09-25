package com.homeapps.diary.utils

import android.annotation.SuppressLint

object Utils {

    @SuppressLint("DefaultLocale")
    fun formatNumberToString(value: Number, pattern: String = "%02d"): String {
        return String.format(pattern, value)
    }

    fun String.countTextCharacters() = this.filterNot { it.isWhitespace() }.length

    fun String.countTextWords() = if (this.isBlank()) 0 else this.trim().split("\\s+".toRegex()).size
}