package com.homeapps.diary.common.di

import com.homeapps.diary.data.clients.ApiClient
import com.homeapps.diary.data.clients.DatabaseDriver
import com.homeapps.diary.data.datasources.alarms.AlarmsDatabaseDao
import com.homeapps.diary.data.datasources.notes.NotesDatabaseDao
import com.homeapps.diary.data.datasources.settings.DiaryDataStore
import com.homeapps.diary.data.datasources.weather.remote.WeatherApi
import com.homeapps.diary.data.jobs.DiaryAlarmScheduler
import com.homeapps.diary.data.repositories.AlarmRepositoryImpl
import com.homeapps.diary.data.repositories.NotesRepositoryImpl
import com.homeapps.diary.data.repositories.SettingsRepositoryImpl
import com.homeapps.diary.data.repositories.WeatherRepositoryImpl
import com.homeapps.diary.domain.api.AlarmRepository
import com.homeapps.diary.domain.api.NotesRepository
import com.homeapps.diary.domain.api.SettingsRepository
import com.homeapps.diary.domain.api.WeatherRepository
import com.homeapps.diary.domain.usecases.alarm.AddAlarmUseCase
import com.homeapps.diary.domain.usecases.alarm.GetAllAlarmsUseCase
import com.homeapps.diary.domain.usecases.alarm.RemoveAlarmUseCase
import com.homeapps.diary.domain.usecases.alarm.RemoveAllAlarmsUseCase
import com.homeapps.diary.domain.usecases.notes.CreateNewNoteUseCase
import com.homeapps.diary.domain.usecases.notes.DeleteNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.GetNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.GetNotesFlowUseCase
import com.homeapps.diary.domain.usecases.notes.UpdateNoteUseCase
import com.homeapps.diary.domain.usecases.settings.GetDarkThemeUseCase
import com.homeapps.diary.domain.usecases.settings.SetDarkThemeUseCase
import com.homeapps.diary.domain.usecases.weather.GetForecastUseCase
import com.homeapps.diary.domain.usecases.weather.GetIpAddressUseCase
import com.homeapps.diary.ui.features.home.HomeViewModel
import com.homeapps.diary.ui.features.homealarm.AlarmViewModel
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
    single { DiaryAlarmScheduler(appContext = androidContext()) }

    single<SettingsRepository> { SettingsRepositoryImpl(diaryDataStore = get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(weatherApi = WeatherApi(apiClient = get())) }
    single<NotesRepository> { NotesRepositoryImpl(dao = NotesDatabaseDao(databaseDriver = get())) }
    single<AlarmRepository> { AlarmRepositoryImpl(dao = AlarmsDatabaseDao(databaseDriver = get())) }

    single { GetDarkThemeUseCase(settingsRepository = get()) }

    viewModel {
        HomeViewModel(
            appContext = androidContext(),
            getDarkThemeUseCase = get(),
            setDarkThemeUseCase = SetDarkThemeUseCase(settingsRepository = get())
        )
    }
    viewModel {
        AlarmViewModel(
            appContext = androidContext(),
            getAllAlarmUseCase = GetAllAlarmsUseCase(alarmRepository = get()),
            addAlarmUseCase = AddAlarmUseCase(alarmRepository = get(), alarmScheduler = get()),
            removeAlarmUseCase = RemoveAlarmUseCase(alarmRepository = get(), alarmScheduler = get()),
            removeAllAlarmsUseCase = RemoveAllAlarmsUseCase(alarmRepository = get(), alarmScheduler = get()),
        )
    }
    viewModel {
        NoteListViewModel(
            createNewNoteUseCase = CreateNewNoteUseCase(notesRepository = get()),
            deleteNoteByIdUseCase = DeleteNoteByIdUseCase(notesRepository = get()),
            getNotesFlowUseCase = GetNotesFlowUseCase(notesRepository = get())
        )
    }
    viewModel {
        NoteDetailViewModel(
            appContext = androidContext(),
            getNoteByIdUseCase = GetNoteByIdUseCase(notesRepository = get()),
            updateNoteUseCase = UpdateNoteUseCase(notesRepository = get())
        )
    }
    viewModel {
        WeatherViewModel(
            appContext = androidContext(),
            getIpAddressUseCase = GetIpAddressUseCase(weatherRepository = get()),
            getForecastUseCase = GetForecastUseCase(weatherRepository = get())
        )
    }
}