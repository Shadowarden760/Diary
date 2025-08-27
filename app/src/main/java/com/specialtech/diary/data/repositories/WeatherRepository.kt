package com.specialtech.diary.data.repositories

import com.specialtech.diary.data.datasources.weather.WeatherDataSource

class WeatherRepository(private val weatherDataSource: WeatherDataSource) {

    suspend fun getIpAddress() = weatherDataSource.getIpAddress()

    suspend fun getForecast(qParam: String, userLocale: String) = weatherDataSource.getForecast(qParam, userLocale)

}