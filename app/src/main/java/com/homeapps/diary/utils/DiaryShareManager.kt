package com.homeapps.diary.utils

import android.content.Context
import android.content.Intent

class DiaryShareManager(private val appContext: Context) {

    fun shareTextData(textData: String, chooserTitle: String): Boolean {
        return runCatching {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                setType(TEXT_TYPE)
                putExtra(Intent.EXTRA_TEXT, textData)
            }
            val chooserIntent = Intent.createChooser(shareIntent, chooserTitle)
            appContext.startActivity(chooserIntent)
            true
        }.getOrDefault(false)
    }

    companion object {
        private const val TEXT_TYPE = "text/plain"
    }
}