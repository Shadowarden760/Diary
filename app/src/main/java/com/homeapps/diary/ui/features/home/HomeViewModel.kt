package com.homeapps.diary.ui.features.home

import android.Manifest
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.domain.usecases.settings.GetDarkThemeUseCase
import com.homeapps.diary.domain.usecases.settings.SetDarkThemeUseCase
import com.homeapps.diary.utils.AppLanguage
import com.homeapps.diary.utils.DiaryNotificationManager
import com.homeapps.diary.utils.LanguageManager
import kotlinx.coroutines.launch

class HomeViewModel(
    private val appContext: Context,
    private val setDarkThemeUseCase: SetDarkThemeUseCase,
    getDarkThemeUseCase: GetDarkThemeUseCase,
): ViewModel() {
    private val languageManager = LanguageManager(appContext = appContext)
    private val notificationManager = DiaryNotificationManager(appContext = appContext)
    val darkTheme = getDarkThemeUseCase()

    fun changeLanguage(newLanguage: AppLanguage) {
        languageManager.changeLanguage(language = newLanguage)
    }

    fun setDarkTheme(darkTheme: Boolean) = viewModelScope.launch {
        setDarkThemeUseCase.invoke(darkTheme = darkTheme)
    }

    fun hasNotificationPermission() = notificationManager.hasNotificationPermission()

    fun requestNotificationPermission(launcher: ActivityResultLauncher<String>) {
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}