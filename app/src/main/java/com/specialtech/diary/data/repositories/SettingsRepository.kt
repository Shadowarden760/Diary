package com.specialtech.diary.data.repositories

import com.specialtech.diary.data.datasources.settings.AppDataStore
import com.specialtech.diary.data.datasources.settings.SettingsDataSource
import kotlinx.coroutines.flow.Flow

class SettingsRepository(private val appDataStore: AppDataStore): SettingsDataSource {

    override fun getDarkTheme(): Flow<Boolean> {
        return appDataStore.darkTheme
    }

    override suspend fun setDarkTheme(darkTheme: Boolean) {
        appDataStore.setDarkTheme(darkTheme = darkTheme)
    }

}