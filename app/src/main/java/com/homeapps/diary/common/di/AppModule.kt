package com.homeapps.diary.common.di

import com.homeapps.diary.data.datasources.database.DatabaseDriver
import com.homeapps.diary.data.datasources.network.ApiClient
import com.homeapps.diary.data.datasources.notes.local.NotesDatabaseData
import com.homeapps.diary.data.datasources.settings.AppDataStore
import com.homeapps.diary.data.datasources.weather.remote.WeatherApiService
import com.homeapps.diary.data.repositories.NotesRepository
import com.homeapps.diary.data.repositories.SettingsRepository
import com.homeapps.diary.data.repositories.WeatherRepository
import com.homeapps.diary.ui.features.home.HomeViewModel
import com.homeapps.diary.ui.features.notedetail.NoteDetailViewModel
import com.homeapps.diary.ui.features.notelist.NoteListViewModel
import com.homeapps.diary.ui.features.weather.WeatherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ApiClient() }
    single { DatabaseDriver(appContext = androidContext()) }
    single { AppDataStore(appContext = androidContext()) }

    single { SettingsRepository(appDataStore = get()) }
    single { WeatherRepository(weatherDataSource = WeatherApiService(apiClient = get())) }
    single { NotesRepository(notesDataSource = NotesDatabaseData(databaseDriver = get())) }

    viewModel { HomeViewModel(settings = get(), appContext = androidContext()) }
    viewModel { NoteListViewModel(notesRepository = get()) }
    viewModel { NoteDetailViewModel(notesRepository = get(), appContext = androidContext()) }
    viewModel { WeatherViewModel(weatherRepository = get(), appContext = androidContext()) }
}