package com.homeapps.diary.domain.usecases.settings

import com.homeapps.diary.domain.api.SettingsDataApi

class SetDarkThemeUseCase(private val settingsDataApi: SettingsDataApi) {

    suspend operator fun invoke(darkTheme: Boolean) {
        settingsDataApi.setDarkTheme(darkTheme)
    }
}