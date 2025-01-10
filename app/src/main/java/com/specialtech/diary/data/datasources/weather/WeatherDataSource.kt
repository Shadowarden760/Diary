package com.specialtech.diary.data.datasources.weather

import com.specialtech.diary.data.datasources.weather.models.IpAddress
import com.specialtech.diary.data.datasources.weather.models.WeatherData

interface WeatherDataSource {

    suspend fun getIpAddress(): IpAddress

    suspend fun getForecast(ipAddress: String, userLocale: String): WeatherData

}