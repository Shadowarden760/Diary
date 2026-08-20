package com.homeapps.diary.ui.features.home

import android.Manifest
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.homeapps.diary.domain.usecases.logging.CreateLogMessageUseCase
import com.homeapps.diary.domain.usecases.logging.DeleteOldLogMessagesUseCase
import com.homeapps.diary.domain.usecases.logging.GetLogMessagesUseCase
import com.homeapps.diary.utils.AppLanguage
import com.homeapps.diary.utils.DiaryNotificationManager
import com.homeapps.diary.utils.LanguageManager

class HomeViewModel(
    private val appContext: Context,
    private val createLogMessageUseCase: CreateLogMessageUseCase,
    private val getLogMessagesUseCase: GetLogMessagesUseCase,
    private val deleteOldLogMessagesUseCase: DeleteOldLogMessagesUseCase
): ViewModel() {
    private val languageManager = LanguageManager(appContext = appContext)
    private val notificationManager = DiaryNotificationManager(appContext = appContext)

    fun changeLanguage(newLanguage: AppLanguage) {
        languageManager.changeLanguage(language = newLanguage)
    }

    fun hasNotificationPermission() = notificationManager.hasNotificationPermission()

    fun requestNotificationPermission(launcher: ActivityResultLauncher<String>) {
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}