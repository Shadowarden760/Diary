package com.homeapps.diary.ui.features.home

import android.Manifest
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.homeapps.diary.domain.api.LoggingRepository
import com.homeapps.diary.domain.usecases.logging.CreateLogMessageUseCase
import com.homeapps.diary.domain.usecases.logging.GetLogMessagesUseCase
import com.homeapps.diary.domain.workers.LogDeleteWorker
import com.homeapps.diary.domain.workers.LogSaveWorker
import com.homeapps.diary.ui.BaseViewModel
import com.homeapps.diary.utils.AppLanguage
import com.homeapps.diary.utils.DiaryFileManager
import com.homeapps.diary.utils.DiaryNotificationManager
import com.homeapps.diary.utils.LanguageManager
import kotlinx.coroutines.flow.Flow
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
    private val diaryFileManager = DiaryFileManager(appContext = getAppContext())

    init {
        val workRequest = PeriodicWorkRequestBuilder<LogDeleteWorker>(
            repeatInterval = 1.hours.toJavaDuration()
        ).build()
        WorkManager.getInstance(context = getAppContext()).enqueueUniquePeriodicWork(
            uniqueWorkName = "diary_delete_log_work",
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.REPLACE,
            request = workRequest
        )
    }

    fun changeLanguage(newLanguage: AppLanguage) {
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${HomeViewModel::class.java}: changing language to ${newLanguage.displayLanguage}"
        )
        languageManager.changeLanguage(language = newLanguage)
    }

    fun hasStoragePermissions()= diaryFileManager.hasStoragePermissions()

    fun requestStoragePermissions(launcher: ActivityResultLauncher<Array<String>>) {
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${HomeViewModel::class.java}: requesting storage permission..."
        )
        launcher.launch(diaryFileManager.storagePermissions)
    }

    fun hasNotificationPermission() = notificationManager.hasNotificationPermission()

    fun requestNotificationPermission(launcher: ActivityResultLauncher<String>) {
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${HomeViewModel::class.java}: requesting notification permission..."
        )
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    fun saveLogDataToTXT(): Flow<WorkInfo?> {
        val workManager = WorkManager.getInstance(context = getAppContext())
        val workRequest = OneTimeWorkRequestBuilder<LogSaveWorker>().build()
        workManager.enqueueUniqueWork(
            uniqueWorkName = "diary_save_logs_to_txt",
            existingWorkPolicy = ExistingWorkPolicy.APPEND_OR_REPLACE,
            request = workRequest
        )
        return workManager.getWorkInfoByIdFlow(workRequest.id)
    }
}