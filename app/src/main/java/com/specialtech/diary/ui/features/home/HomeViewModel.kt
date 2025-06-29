package com.specialtech.diary.ui.features.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specialtech.diary.data.repositories.SettingsRepository
import com.specialtech.diary.utils.AppLanguage
import com.specialtech.diary.utils.LanguageManager
import kotlinx.coroutines.launch

class HomeViewModel(
    private val settings: SettingsRepository,
    private val appContext: Context
): ViewModel() {
    private val languageManager = LanguageManager()
    val darkTheme = settings.getDarkTheme()

    fun changeLanguage(newLanguage: AppLanguage) {
        languageManager.changeLanguage(context = appContext, language = newLanguage)
    }

    fun setDarkTheme(darkTheme: Boolean) = viewModelScope.launch {
        settings.setDarkTheme(darkTheme = darkTheme)
    }

}