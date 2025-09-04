package com.homeapps.diary.ui.features.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.domain.usecases.settings.GetDarkThemeUseCase
import com.homeapps.diary.domain.usecases.settings.SetDarkThemeUseCase
import com.homeapps.diary.utils.AppLanguage
import com.homeapps.diary.utils.LanguageManager
import kotlinx.coroutines.launch

class HomeViewModel(
    private val appContext: Context,
    private val setDarkThemeUseCase: SetDarkThemeUseCase,
    getDarkThemeUseCase: GetDarkThemeUseCase,
): ViewModel() {
    private val languageManager = LanguageManager(appContext = appContext)
    val darkTheme = getDarkThemeUseCase()

    fun changeLanguage(newLanguage: AppLanguage) {
        languageManager.changeLanguage(language = newLanguage)
    }

    fun setDarkTheme(darkTheme: Boolean) = viewModelScope.launch {
        setDarkThemeUseCase.invoke(darkTheme = darkTheme)
    }
}