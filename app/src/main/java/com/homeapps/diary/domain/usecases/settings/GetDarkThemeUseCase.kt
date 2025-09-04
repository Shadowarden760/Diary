package com.homeapps.diary.domain.usecases.settings

import com.homeapps.diary.domain.api.SettingsDataApi
import kotlinx.coroutines.flow.Flow

class GetDarkThemeUseCase(private val settingsDataApi: SettingsDataApi) {

    operator fun invoke(): Flow<Boolean> {
        return settingsDataApi.getDarkTheme()
    }
}