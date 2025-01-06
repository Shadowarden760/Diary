package com.specialtech.diary.data.datasources.weather

import com.specialtech.diary.data.datasources.weather.models.WeatherData

interface WeatherDataSource {

    suspend fun getIpAddress(): String?

    suspend fun getForecast(ipAddress: String): WeatherData

}