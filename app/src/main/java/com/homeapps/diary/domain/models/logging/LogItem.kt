package com.homeapps.diary.domain.models.logging

import com.homeapps.diary.domain.api.LoggingRepository

class LogItem(
    val logLevel: LoggingRepository.LogLevel,
    val logMessage: String,
    val logMessageCreatedAt: Long
)