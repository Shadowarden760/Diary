package com.specialtech.diary.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

data class AppLanguage(
    val code: String,
    val displayLanguage: String
)

val appLanguages = listOf(
    AppLanguage("en", "English"), // default language
    AppLanguage("ru", "Русский")
)

class LanguageManager {

    fun changeLanguage(context: Context, language: AppLanguage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(language.code)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language.code))
        }
    }

    fun getLanguage(context: Context): AppLanguage {
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)?.applicationLocales?.get(0)
        } else {
            AppCompatDelegate.getApplicationLocales()[0]
        }
        return appLanguages.find { it.code == locale?.language } ?: appLanguages.first()
    }

}