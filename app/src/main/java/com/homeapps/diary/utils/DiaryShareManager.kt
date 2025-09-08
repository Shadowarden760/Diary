package com.homeapps.diary.utils

import android.content.Context
import android.content.Intent

class DiaryShareManager() {

    fun shareTextData(textData: String, chooserTitle: String, context: Context): Boolean {
        return runCatching {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                setType(TEXT_TYPE)
                putExtra(Intent.EXTRA_TEXT, textData)
            }
            val chooserIntent = Intent.createChooser(shareIntent, chooserTitle)
            context.startActivity(chooserIntent)
            true
        }.getOrDefault(false)
    }

    companion object {
        private const val TEXT_TYPE = "text/plain"
    }
}