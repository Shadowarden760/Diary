package com.homeapps.diary.common.di

import com.homeapps.diary.data.clients.DatabaseDriver
import com.homeapps.diary.data.clients.ApiClient
import com.homeapps.diary.data.datasources.notes.NotesDatabaseDao
import com.homeapps.diary.data.datasources.settings.DiaryDataStore
import com.homeapps.diary.data.datasources.weather.remote.WeatherApi
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
    single { DiaryDataStore(appContext = androidContext()) }

    single { SettingsRepository(diaryDataStore = get()) }
    single { WeatherRepository(weatherDataSource = WeatherApi(apiClient = get())) }
    single { NotesRepository(notesDataSource = NotesDatabaseDao(databaseDriver = get())) }

    viewModel { HomeViewModel(settings = get(), appContext = androidContext()) }
    viewModel { NoteListViewModel(notesRepository = get()) }
    viewModel { NoteDetailViewModel(notesRepository = get(), appContext = androidContext()) }
    viewModel { WeatherViewModel(weatherRepository = get(), appContext = androidContext()) }
}