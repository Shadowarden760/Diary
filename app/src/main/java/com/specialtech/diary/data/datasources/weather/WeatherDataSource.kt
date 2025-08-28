package com.specialtech.diary.data.datasources.weather

import com.specialtech.diary.data.datasources.weather.models.IpAddressData
import com.specialtech.diary.data.datasources.weather.models.WeatherData

interface WeatherDataSource {

    suspend fun getIpAddress(): IpAddressData

    suspend fun getForecast(qParam: String, userLocale: String): WeatherData?

}