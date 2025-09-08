package com.homeapps.diary.domain.usecases.settings

import com.homeapps.diary.domain.api.SettingsRepository

class SetDarkThemeUseCase(private val settingsRepository: SettingsRepository) {

    suspend operator fun invoke(darkTheme: Boolean) {
        settingsRepository.setDarkTheme(darkTheme)
    }
}