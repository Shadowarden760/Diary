package com.homeapps.diary.data.datasources.weather

import com.homeapps.diary.data.datasources.weather.models.IpAddressData
import com.homeapps.diary.data.datasources.weather.models.WeatherData

interface WeatherDataSource {

    suspend fun getIpAddress(): IpAddressData

    suspend fun getForecast(qParam: String, userLocale: String): WeatherData?
}