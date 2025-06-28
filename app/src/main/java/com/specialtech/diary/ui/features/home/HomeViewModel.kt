package com.specialtech.diary.ui.features.home

import com.specialtech.diary.utils.AppLanguage
import com.specialtech.diary.utils.LanguageManager
import android.content.Context
import androidx.lifecycle.ViewModel

class HomeViewModel(private val appContext: Context): ViewModel() {
    private val languageManager = LanguageManager()

    fun changeLanguage(newLanguage: AppLanguage) {
        languageManager.changeLanguage(context = appContext, language = newLanguage)
    }

}