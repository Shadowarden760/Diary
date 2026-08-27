package com.homeapps.diary.ui.features.home

import android.Manifest
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.homeapps.diary.domain.usecases.logging.CreateLogMessageUseCase
import com.homeapps.diary.domain.usecases.logging.GetLogMessagesUseCase
import com.homeapps.diary.domain.workers.LoggingWorker
import com.homeapps.diary.ui.BaseViewModel
import com.homeapps.diary.utils.AppLanguage
import com.homeapps.diary.utils.DiaryNotificationManager
import com.homeapps.diary.utils.LanguageManager
import kotlin.time.Duration.Companion.hours
import kotlin.time.toJavaDuration

class HomeViewModel(
    appContext: Context,
    createLogMessageUseCase: CreateLogMessageUseCase,
    getLogMessagesUseCase: GetLogMessagesUseCase,
): BaseViewModel(
    appContext = appContext,
    createLogMessageUseCase = createLogMessageUseCase,
    getLogMessagesUseCase = getLogMessagesUseCase,
) {
    private val languageManager = LanguageManager(appContext = getAppContext())
    private val notificationManager = DiaryNotificationManager(appContext = getAppContext())

    init {
        val workRequest = PeriodicWorkRequestBuilder<LoggingWorker>(
            repeatInterval = 1.hours.toJavaDuration()
        ).build()
        WorkManager.getInstance(context = getAppContext()).enqueueUniquePeriodicWork(
            uniqueWorkName = "diary_delete_log_work",
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.REPLACE,
            request = workRequest
        )
    }

    fun changeLanguage(newLanguage: AppLanguage) {
        languageManager.changeLanguage(language = newLanguage)
    }

    fun hasNotificationPermission() = notificationManager.hasNotificationPermission()

    fun requestNotificationPermission(launcher: ActivityResultLauncher<String>) {
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}