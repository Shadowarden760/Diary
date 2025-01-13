package com.specialtech.diary.common.di

import com.specialtech.diary.data.datasources.money.local.LocalMoneyData
import com.specialtech.diary.data.datasources.network.ApiClient
import com.specialtech.diary.data.datasources.weather.remote.WeatherApiService
import com.specialtech.diary.data.repositories.MoneyRepository
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
    single { WeatherRepository(weatherDataSource = WeatherApiService(apiClient = get())) }
    single { MoneyRepository(moneyDataSource = LocalMoneyData()) }
    viewModel { HomeViewModel() }
    viewModel { NoteListViewModel() }
    viewModel { NoteDetailViewModel() }
    viewModel { WeatherViewModel(weatherRepository = get()) }
    viewModel { MoneyViewModel(moneyRepository = get()) }
}