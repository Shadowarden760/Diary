package com.homeapps.diary.data.repositories

import com.homeapps.diary.data.datasources.settings.DiaryDataStore
import com.homeapps.diary.domain.api.SettingsDataApi
import kotlinx.coroutines.flow.Flow

class SettingsRepository(private val diaryDataStore: DiaryDataStore): SettingsDataApi {

    override fun getDarkTheme(): Flow<Boolean> = diaryDataStore.darkTheme

    override suspend fun setDarkTheme(darkTheme: Boolean) {
        diaryDataStore.setDarkTheme(darkTheme = darkTheme)
    }
}