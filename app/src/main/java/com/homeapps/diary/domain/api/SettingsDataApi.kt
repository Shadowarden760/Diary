package com.homeapps.diary.domain.api

import kotlinx.coroutines.flow.Flow

interface SettingsDataApi {

    fun getDarkTheme(): Flow<Boolean>

    suspend fun setDarkTheme(darkTheme: Boolean)
}