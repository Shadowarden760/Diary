package com.homeapps.diary.ui.features.home

import android.Manifest
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import com.homeapps.diary.domain.usecases.logging.CreateLogMessageUseCase
import com.homeapps.diary.domain.usecases.logging.DeleteOldLogMessagesUseCase
import com.homeapps.diary.domain.usecases.logging.GetLogMessagesUseCase
import com.homeapps.diary.ui.BaseViewModel
import com.homeapps.diary.utils.AppLanguage
import com.homeapps.diary.utils.DiaryNotificationManager
import com.homeapps.diary.utils.LanguageManager

class HomeViewModel(
    appContext: Context,
    createLogMessageUseCase: CreateLogMessageUseCase,
    getLogMessagesUseCase: GetLogMessagesUseCase,
    deleteOldLogMessagesUseCase: DeleteOldLogMessagesUseCase
): BaseViewModel(
    appContext = appContext,
    createLogMessageUseCase = createLogMessageUseCase,
    getLogMessagesUseCase = getLogMessagesUseCase,
    deleteOldLogMessagesUseCase = deleteOldLogMessagesUseCase
) {
    private val languageManager = LanguageManager(appContext = getAppContext())
    private val notificationManager = DiaryNotificationManager(appContext = getAppContext())

    fun changeLanguage(newLanguage: AppLanguage) {
        languageManager.changeLanguage(language = newLanguage)
    }

    fun hasNotificationPermission() = notificationManager.hasNotificationPermission()

    fun requestNotificationPermission(launcher: ActivityResultLauncher<String>) {
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}