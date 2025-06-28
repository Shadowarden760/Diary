package com.specialtech.diary.common.di

import com.specialtech.diary.data.datasources.database.DatabaseDriver
import com.specialtech.diary.data.datasources.network.ApiClient
import com.specialtech.diary.data.datasources.notes.local.NotesDatabaseData
import com.specialtech.diary.data.datasources.weather.remote.WeatherApiService
import com.specialtech.diary.data.repositories.NotesRepository
import com.specialtech.diary.data.repositories.WeatherRepository
import com.specialtech.diary.ui.features.home.HomeViewModel
import com.specialtech.diary.ui.features.notedetail.NoteDetailViewModel
import com.specialtech.diary.ui.features.notelist.NoteListViewModel
import com.specialtech.diary.ui.features.weather.WeatherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ApiClient() }
    single { DatabaseDriver(context = androidContext()) }

    single { WeatherRepository(weatherDataSource = WeatherApiService(apiClient = get())) }
    single { NotesRepository(notesDataSource = NotesDatabaseData(databaseDriver = get())) }

    viewModel { HomeViewModel(appContext = androidContext()) }
    viewModel { NoteListViewModel(notesRepository = get()) }
    viewModel { NoteDetailViewModel(notesRepository = get()) }
    viewModel { WeatherViewModel(weatherRepository = get()) }
}