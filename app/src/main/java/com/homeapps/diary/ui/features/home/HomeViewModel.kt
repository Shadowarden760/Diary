package com.homeapps.diary.ui.features.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.data.repositories.SettingsRepository
import com.homeapps.diary.utils.AppLanguage
import com.homeapps.diary.utils.LanguageManager
import kotlinx.coroutines.launch

class HomeViewModel(
    private val settings: SettingsRepository,
    private val appContext: Context
): ViewModel() {
    private val languageManager = LanguageManager(appContext = appContext)
    val darkTheme = settings.getDarkTheme()

    fun changeLanguage(newLanguage: AppLanguage) {
        languageManager.changeLanguage(language = newLanguage)
    }

    fun setDarkTheme(darkTheme: Boolean) = viewModelScope.launch {
        settings.setDarkTheme(darkTheme = darkTheme)
    }
}