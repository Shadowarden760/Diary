package com.specialtech.diary.data.datasources.settings

import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {

    fun getDarkTheme(): Flow<Boolean>

    suspend fun setDarkTheme(darkTheme: Boolean)

}