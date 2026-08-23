package com.homeapps.diary.domain.usecases.logging

import com.homeapps.diary.domain.api.LoggingRepository

class CreateLogMessageUseCase(private val loggingRepository: LoggingRepository) {

    suspend operator fun invoke(logMessageLevel: LoggingRepository.LogLevel, logMessageText: String) {
        if (logMessageLevel.weight <= loggingRepository.logLevel.weight) {
            loggingRepository.createLogMessage(
                logMessageLevel = logMessageLevel,
                logMessageText = logMessageText
            )
        }
    }
}