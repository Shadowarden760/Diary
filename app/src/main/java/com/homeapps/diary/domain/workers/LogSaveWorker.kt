package com.homeapps.diary.domain.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.homeapps.diary.domain.api.LoggingRepository
import com.homeapps.diary.domain.usecases.logging.CreateLogMessageUseCase
import com.homeapps.diary.domain.usecases.logging.GetLogMessagesUseCase
import com.homeapps.diary.utils.DateTimeUtils
import com.homeapps.diary.utils.DiaryFileManager

class LogSaveWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val createLogMessageUseCase: CreateLogMessageUseCase,
    private val getLogMessageUseCase: GetLogMessagesUseCase,
): CoroutineWorker(appContext = appContext, workerParams) {
    private val diaryFileManager = DiaryFileManager(appContext = appContext)

    override suspend fun doWork(): Result {
        val result = runCatching {
            createLogMessageUseCase(
                logMessageLevel = LoggingRepository.LogLevel.INFO,
                logMessageText = "Saving log messages..."
            )
            val date = DateTimeUtils.timeMillisToDate(
                timeMillis = System.currentTimeMillis(),
                format = "dd_MM_yyyy_HH_mm_ss"
            )
            val filename = diaryFileManager.saveDataToFile(
                data = getLogMessageUseCase().joinToString(separator = "\n") { it.toString() },
                fileName = "diary_log_${date}.txt"
            )
            if (filename.first) {
                createLogMessageUseCase(
                    logMessageLevel = LoggingRepository.LogLevel.INFO,
                    logMessageText = "Logs were saved to ${filename.second}"
                )
            } else {
                createLogMessageUseCase(
                    logMessageLevel = LoggingRepository.LogLevel.ERROR,
                    logMessageText = "Logs were not saved - ${filename.second}"
                )
            }
        }
        return if (result.isSuccess) Result.success() else Result.failure()
    }

}