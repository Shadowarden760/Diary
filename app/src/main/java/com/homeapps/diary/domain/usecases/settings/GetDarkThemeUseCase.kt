package com.homeapps.diary.domain.usecases.settings

import com.homeapps.diary.domain.api.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetDarkThemeUseCase(private val settingsRepository: SettingsRepository) {

    operator fun invoke(): Flow<Boolean> {
        return settingsRepository.getDarkTheme()
    }
}