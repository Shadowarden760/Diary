package com.specialtech.diary.di

import com.specialtech.diary.data.datasources.weather.local.LocalData
import com.specialtech.diary.data.repositories.WeatherRepository
import com.specialtech.diary.ui.screens.weather.WeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { WeatherRepository(LocalData()) }
    viewModel { WeatherViewModel(get()) }
}