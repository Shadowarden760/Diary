package com.homeapps.diary.domain.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.homeapps.diary.domain.api.LoggingRepository
import com.homeapps.diary.domain.usecases.logging.CreateLogMessageUseCase
import com.homeapps.diary.domain.usecases.logging.DeleteOldLogMessagesUseCase

class LoggingWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val createLogMessageUseCase: CreateLogMessageUseCase,
    private val deleteOldLogMessagesUseCase: DeleteOldLogMessagesUseCase
): CoroutineWorker(appContext = appContext, workerParams) {

    override suspend fun doWork(): Result {
        createLogMessageUseCase(
            logMessageLevel = LoggingRepository.LogLevel.INFO,
            logMessageText = "Deleting old log messages..."
        )
        deleteOldLogMessagesUseCase(thresholdTimeMillis = DEFAULT_LOG_LIFE_MILLIS)
        return Result.success()
    }

    companion object {
        private const val DEFAULT_LOG_LIFE_MILLIS = 1000 * 3600 * 24 * 7L // 1 WEEK
    }
}