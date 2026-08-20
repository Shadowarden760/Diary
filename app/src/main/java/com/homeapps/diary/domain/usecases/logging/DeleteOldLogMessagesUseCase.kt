package com.homeapps.diary.domain.usecases.logging

import com.homeapps.diary.domain.api.LoggingRepository

class DeleteOldLogMessagesUseCase(private val loggingRepository: LoggingRepository) {

    suspend operator fun invoke(thresholdTimeMillis: Long) {
        loggingRepository.deleteOldLogMessages(thresholdTimeMillis = thresholdTimeMillis)
    }
}