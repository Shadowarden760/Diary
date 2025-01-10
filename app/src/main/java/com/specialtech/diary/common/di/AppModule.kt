package com.specialtech.diary.common.di

import com.specialtech.diary.data.datasources.weather.remote.ApiClient
import com.specialtech.diary.data.datasources.weather.remote.ApiService
import com.specialtech.diary.data.repositories.WeatherRepository
import com.specialtech.diary.ui.features.home.HomeViewModel
import com.specialtech.diary.ui.features.money.MoneyViewModel
import com.specialtech.diary.ui.features.notedetail.NoteDetailViewModel
import com.specialtech.diary.ui.features.notelist.NoteListViewModel
import com.specialtech.diary.ui.features.weather.WeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ApiClient() }
    single { WeatherRepository(weatherDataSource = ApiService(apiClient = get())) }
    viewModel { HomeViewModel() }
    viewModel { NoteListViewModel() }
    viewModel { NoteDetailViewModel() }
    viewModel { WeatherViewModel(weatherRepository = get()) }
    viewModel { MoneyViewModel() }
}