package com.specialtech.diary.data.datasources.weather

import com.specialtech.diary.data.datasources.weather.models.FutureWeatherModel
import com.specialtech.diary.data.datasources.weather.models.HourlyWeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherDataSource {

    fun getHourlyWeather(): Flow<List<HourlyWeatherModel>>

    fun getFutureWeather(): Flow<List<FutureWeatherModel>>

}