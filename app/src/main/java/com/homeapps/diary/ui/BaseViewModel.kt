package com.homeapps.diary.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.homeapps.diary.domain.api.LoggingRepository
import com.homeapps.diary.domain.models.logging.LogItem
import com.homeapps.diary.domain.usecases.logging.CreateLogMessageUseCase
import com.homeapps.diary.domain.usecases.logging.DeleteOldLogMessagesUseCase
import com.homeapps.diary.domain.usecases.logging.GetLogMessagesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

open class BaseViewModel(
    private val appContext: Context,
    private val createLogMessageUseCase: CreateLogMessageUseCase,
    private val getLogMessagesUseCase: GetLogMessagesUseCase?,
    private val deleteOldLogMessagesUseCase: DeleteOldLogMessagesUseCase?
): ViewModel(), BaseLogApi {

    fun getAppContext(): Context = appContext

    override fun createLogMessage(logLevel: LoggingRepository.LogLevel, logMessage: String) {
        CoroutineScope(context = Dispatchers.IO).launch {
            createLogMessageUseCase(logMessageLevel = logLevel, logMessageText = logMessage)
        }
    }

    override suspend fun getLogMessages(): List<LogItem>? {
        return CoroutineScope(context = Dispatchers.IO).async {
            getLogMessagesUseCase?.invoke()
        }.await()
    }

    override fun deleteOldLogMessages() {
        CoroutineScope(context = Dispatchers.IO).launch {
            deleteOldLogMessagesUseCase?.invoke(thresholdTimeMillis = DEFAULT_LOG_LIFE_MILLIS)
        }
    }

    companion object {
        private const val DEFAULT_LOG_LIFE_MILLIS = 1000 * 3600 * 24 * 7L
    }
}

private interface BaseLogApi {

    fun createLogMessage(logLevel: LoggingRepository.LogLevel, logMessage: String)

    suspend fun getLogMessages(): List<LogItem>?

    fun deleteOldLogMessages()
}