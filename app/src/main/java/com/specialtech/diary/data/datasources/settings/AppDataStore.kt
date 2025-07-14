package com.specialtech.diary.data.datasources.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDataStore(private val appContext: Context) {
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

    val darkTheme: Flow<Boolean> = appContext.datastore.data.map { preferences ->
        preferences[SettingsKeys.DARK_THEME_KEY] ?: false
    }

    suspend fun setDarkTheme(darkTheme: Boolean) {
        appContext.datastore.edit { preferences ->
            preferences[SettingsKeys.DARK_THEME_KEY] = darkTheme
        }
    }

    private object SettingsKeys {
        val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_key")
    }

}