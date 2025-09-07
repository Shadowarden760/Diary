package com.homeapps.diary.data.repositories

import com.homeapps.diary.data.datasources.settings.DiaryDataStore
import com.homeapps.diary.domain.api.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(private val diaryDataStore: DiaryDataStore): SettingsRepository {

    override fun getDarkTheme(): Flow<Boolean> = diaryDataStore.darkTheme

    override suspend fun setDarkTheme(darkTheme: Boolean) {
        diaryDataStore.setDarkTheme(darkTheme = darkTheme)
    }
}