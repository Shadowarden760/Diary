package com.homeapps.diary.domain.api

import com.homeapps.diary.domain.models.weather.IpAddressData
import com.homeapps.diary.domain.models.weather.WeatherData

interface WeatherDataApi {

    suspend fun getIpAddress(): IpAddressData

    suspend fun getForecast(qParam: String, locale: String): WeatherData?
}