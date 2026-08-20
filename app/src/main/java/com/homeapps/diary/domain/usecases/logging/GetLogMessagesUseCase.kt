package com.homeapps.diary.domain.usecases.logging

import com.homeapps.diary.data.mappers.toLogItem
import com.homeapps.diary.domain.api.LoggingRepository
import com.homeapps.diary.domain.models.logging.LogItem

class GetLogMessagesUseCase(private val loggingRepository: LoggingRepository) {

    suspend operator fun invoke(): List<LogItem> {
        return loggingRepository.getAllLogMessages().map { it.toLogItem() }
    }
}