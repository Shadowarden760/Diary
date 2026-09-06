package com.homeapps.diary.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.homeapps.diary.domain.api.LoggingRepository
import com.homeapps.diary.domain.models.logging.LogItem
import com.homeapps.diary.domain.usecases.logging.CreateLogMessageUseCase
import com.homeapps.diary.domain.usecases.logging.GetLogMessagesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


open class BaseViewModel(
    private val appContext: Context,
    private val createLogMessageUseCase: CreateLogMessageUseCase,
    private val getLogMessagesUseCase: GetLogMessagesUseCase?,
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
}

private interface BaseLogApi {

    fun createLogMessage(logLevel: LoggingRepository.LogLevel, logMessage: String)

    suspend fun getLogMessages(): List<LogItem>?
}