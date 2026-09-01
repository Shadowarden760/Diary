package com.homeapps.diary.common.di

import com.homeapps.diary.common.navigation.NavViewModel
import com.homeapps.diary.data.clients.ApiClient
import com.homeapps.diary.data.clients.DatabaseDriver
import com.homeapps.diary.data.datasources.alarms.AlarmsDatabaseDao
import com.homeapps.diary.data.datasources.logging.LogDatabaseDao
import com.homeapps.diary.data.datasources.notes.NotesDatabaseDao
import com.homeapps.diary.data.datasources.settings.DiaryDataStore
import com.homeapps.diary.data.datasources.weather.remote.WeatherApi
import com.homeapps.diary.data.jobs.AlarmSchedulerImpl
import com.homeapps.diary.data.repositories.AlarmRepositoryImpl
import com.homeapps.diary.data.repositories.LoggingRepositoryImpl
import com.homeapps.diary.data.repositories.NotesRepositoryImpl
import com.homeapps.diary.data.repositories.SettingsRepositoryImpl
import com.homeapps.diary.data.repositories.WeatherRepositoryImpl
import com.homeapps.diary.domain.api.AlarmRepository
import com.homeapps.diary.domain.api.AlarmScheduler
import com.homeapps.diary.domain.api.LoggingRepository
import com.homeapps.diary.domain.api.NotesRepository
import com.homeapps.diary.domain.api.SettingsRepository
import com.homeapps.diary.domain.api.WeatherRepository
import com.homeapps.diary.domain.usecases.alarm.AddAlarmUseCase
import com.homeapps.diary.domain.usecases.alarm.GetAllAlarmsUseCase
import com.homeapps.diary.domain.usecases.alarm.RemoveAlarmUseCase
import com.homeapps.diary.domain.usecases.alarm.RemoveAllAlarmsUseCase
import com.homeapps.diary.domain.usecases.logging.CreateLogMessageUseCase
import com.homeapps.diary.domain.usecases.logging.DeleteOldLogMessagesUseCase
import com.homeapps.diary.domain.usecases.logging.GetLogMessagesUseCase
import com.homeapps.diary.domain.usecases.notes.CreateNewNoteUseCase
import com.homeapps.diary.domain.usecases.notes.DeleteNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.GetNoteByIdUseCase
import com.homeapps.diary.domain.usecases.notes.GetNotesFlowUseCase
import com.homeapps.diary.domain.usecases.notes.UpdateNoteUseCase
import com.homeapps.diary.domain.usecases.settings.GetDarkThemeUseCase
import com.homeapps.diary.domain.usecases.settings.SetDarkThemeUseCase
import com.homeapps.diary.domain.usecases.weather.GetForecastUseCase
import com.homeapps.diary.domain.usecases.weather.GetIpAddressUseCase
import com.homeapps.diary.domain.workers.LogDeleteWorker
import com.homeapps.diary.domain.workers.LogSaveWorker
import com.homeapps.diary.ui.features.home.HomeViewModel
import com.homeapps.diary.ui.features.homealarm.AlarmViewModel
import com.homeapps.diary.ui.features.notedetail.NoteDetailViewModel
import com.homeapps.diary.ui.features.notelist.NoteListViewModel
import com.homeapps.diary.ui.features.weather.WeatherViewModel
import com.homeapps.diary.ui.theme.ThemeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ApiClient() }
    single { DatabaseDriver(appContext = androidContext()) }
    single { DiaryDataStore(appContext = androidContext()) }

    single<SettingsRepository> { SettingsRepositoryImpl(diaryDataStore = get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(weatherApi = WeatherApi(apiClient = get())) }
    single<NotesRepository> { NotesRepositoryImpl(dao = NotesDatabaseDao(databaseDriver = get())) }
    single<AlarmRepository> { AlarmRepositoryImpl(dao = AlarmsDatabaseDao(databaseDriver = get())) }
    single<AlarmScheduler> { AlarmSchedulerImpl(appContext = androidContext()) }

    single<LoggingRepository> { LoggingRepositoryImpl(dao = LogDatabaseDao(databaseDriver = get())) }
    single<CreateLogMessageUseCase> { CreateLogMessageUseCase(loggingRepository = get()) }
    single<GetLogMessagesUseCase> { GetLogMessagesUseCase(loggingRepository = get()) }
    single<DeleteOldLogMessagesUseCase> { DeleteOldLogMessagesUseCase(loggingRepository = get()) }

    viewModel {
        NavViewModel()
    }
    viewModel {
        ThemeViewModel(
            getDarkThemeUseCase = GetDarkThemeUseCase(settingsRepository = get()),
            setDarkThemeUseCase = SetDarkThemeUseCase(settingsRepository = get()),
            createLogMessageUseCase = get()
        )
    }
    viewModel {
        HomeViewModel(
            appContext = androidContext(),
            createLogMessageUseCase = get(),
            getLogMessagesUseCase = get()
        )
    }
    viewModel {
        AlarmViewModel(
            appContext = androidContext(),
            getAllAlarmUseCase = GetAllAlarmsUseCase(alarmRepository = get()),
            addAlarmUseCase = AddAlarmUseCase(alarmRepository = get(), alarmScheduler = get()),
            removeAlarmUseCase = RemoveAlarmUseCase(alarmRepository = get(), alarmScheduler = get()),
            removeAllAlarmsUseCase = RemoveAllAlarmsUseCase(alarmRepository = get(), alarmScheduler = get()),
            createLogMessageUseCase = get()
        )
    }
    viewModel {
        NoteListViewModel(
            appContext = androidContext(),
            createNewNoteUseCase = CreateNewNoteUseCase(notesRepository = get()),
            deleteNoteByIdUseCase = DeleteNoteByIdUseCase(notesRepository = get()),
            getNotesFlowUseCase = GetNotesFlowUseCase(notesRepository = get()),
            updateNoteUseCase = UpdateNoteUseCase(notesRepository = get()),
            createLogMessageUseCase = get()
        )
    }
    viewModel {
        NoteDetailViewModel(
            appContext = androidContext(),
            getNoteByIdUseCase = GetNoteByIdUseCase(notesRepository = get()),
            updateNoteUseCase = UpdateNoteUseCase(notesRepository = get()),
            createLogMessageUseCase = get()
        )
    }
    viewModel {
        WeatherViewModel(
            appContext = androidContext(),
            getIpAddressUseCase = GetIpAddressUseCase(weatherRepository = get()),
            getForecastUseCase = GetForecastUseCase(weatherRepository = get()),
            createLogMessageUseCase = get()
        )
    }

    worker<LogDeleteWorker> { params ->
        LogDeleteWorker(
            appContext = androidContext(),
            workerParams = params.get(),
            createLogMessageUseCase = get(),
            deleteOldLogMessagesUseCase = get()
        )
    }

    worker< LogSaveWorker> { params ->
        LogSaveWorker(
            appContext = androidContext(),
            workerParams = params.get(),
            createLogMessageUseCase = get(),
            getLogMessageUseCase = get()
        )
    }
}