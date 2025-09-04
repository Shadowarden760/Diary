package com.homeapps.diary.common.di

import com.homeapps.diary.data.clients.ApiClient
import com.homeapps.diary.data.clients.DatabaseDriver
import com.homeapps.diary.data.datasources.notes.NotesDatabaseDao
import com.homeapps.diary.data.datasources.settings.DiaryDataStore
import com.homeapps.diary.data.datasources.weather.remote.WeatherApi
import com.homeapps.diary.data.repositories.NotesRepository
import com.homeapps.diary.data.repositories.SettingsRepository
import com.homeapps.diary.data.repositories.WeatherRepository
import com.homeapps.diary.domain.api.NotesDataApi
import com.homeapps.diary.domain.api.SettingsDataApi
import com.homeapps.diary.domain.api.WeatherDataApi
import com.homeapps.diary.domain.usecases.settings.GetDarkThemeUseCase
import com.homeapps.diary.domain.usecases.settings.SetDarkThemeUseCase
import com.homeapps.diary.domain.usecases.notes.CreateNewNoteUseCase
import com.homeapps.diary.domain.usecases.notes.DeleteNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.GetNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.GetNotesFlowUseCase
import com.homeapps.diary.domain.usecases.notes.UpdateNoteUseCase
import com.homeapps.diary.domain.usecases.weather.GetForecastUseCase
import com.homeapps.diary.domain.usecases.weather.GetIpAddressUseCase
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

    single<SettingsDataApi> { SettingsRepository(diaryDataStore = get()) }
    single<WeatherDataApi> { WeatherRepository(weatherApi = WeatherApi(apiClient = get())) }
    single<NotesDataApi> { NotesRepository(dao = NotesDatabaseDao(databaseDriver = get())) }

    single { GetDarkThemeUseCase(settingsDataApi = get()) }

    viewModel {
        HomeViewModel(
            appContext = androidContext(),
            getDarkThemeUseCase = get(),
            setDarkThemeUseCase = SetDarkThemeUseCase(settingsDataApi = get())
        )
    }
    viewModel {
        NoteListViewModel(
            createNewNoteUseCase = CreateNewNoteUseCase(notesDataApi = get()),
            deleteNoteByIdUseCase = DeleteNoteByIdUseCase(notesDataApi = get()),
            getNotesFlowUseCase = GetNotesFlowUseCase(notesDataApi = get())
        )
    }
    viewModel {
        NoteDetailViewModel(
            appContext = androidContext(),
            getNoteByIdUseCase = GetNoteByIdUseCase(notesDataApi = get()),
            updateNoteUseCase = UpdateNoteUseCase(notesDataApi = get())
        )
    }
    viewModel {
        WeatherViewModel(
            appContext = androidContext(),
            getIpAddressUseCase = GetIpAddressUseCase(weatherDataApi = get()),
            getForecastUseCase = GetForecastUseCase(weatherDataApi = get())
        )
    }
}