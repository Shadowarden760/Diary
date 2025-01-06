package com.specialtech.diary.di

import com.specialtech.diary.data.datasources.weather.remote.ApiClient
import com.specialtech.diary.data.datasources.weather.remote.ApiService
import com.specialtech.diary.data.repositories.WeatherRepository
import com.specialtech.diary.ui.screens.weather.WeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ApiClient() }
    single { WeatherRepository(ApiService(get())) }
    viewModel { WeatherViewModel(get()) }
}