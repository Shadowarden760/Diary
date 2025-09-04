package com.homeapps.diary.data.repositories

import com.homeapps.diary.data.datasources.weather.remote.WeatherApi
import com.homeapps.diary.domain.api.WeatherDataApi
import com.homeapps.diary.domain.models.weather.IpAddressData
import com.homeapps.diary.domain.models.weather.WeatherData

class WeatherRepository(private val weatherApi: WeatherApi): WeatherDataApi {

    override suspend fun getIpAddress(): IpAddressData = weatherApi.getIpAddress()

    override suspend fun getForecast(qParam: String, locale: String): WeatherData? {
        return weatherApi.getForecast(qParam, locale)
    }
}