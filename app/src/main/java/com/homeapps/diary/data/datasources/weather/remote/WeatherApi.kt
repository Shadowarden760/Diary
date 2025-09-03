package com.homeapps.diary.data.datasources.weather.remote

import com.homeapps.diary.BuildConfig
import com.homeapps.diary.data.clients.ApiClient
import com.homeapps.diary.data.datasources.weather.WeatherDataSource
import com.homeapps.diary.domain.models.IpAddressData
import com.homeapps.diary.domain.models.WeatherData
import com.homeapps.diary.data.datasources.weather.models.dto.ForecastDTO
import com.homeapps.diary.data.datasources.weather.models.dto.IpAddressDTO
import com.homeapps.diary.data.mappers.toWeatherData
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class WeatherApi(private val apiClient: ApiClient): WeatherDataSource {

    override suspend fun getIpAddress(): IpAddressData {
        return runCatching {
            val response = apiClient.httpClient.get(IP_URL)
            if (response.status.value == 200) {
                val ip = response.body<IpAddressDTO>().ipAddress
                IpAddressData(ip = ip)
            } else {
                IpAddressData(ip = null, errorMessage = response.bodyAsText())
            }
        }.getOrDefault(IpAddressData(ip = null))
    }

    override suspend fun getForecast(qParam: String, userLocale: String): WeatherData? {
        return runCatching {
            val response = apiClient.httpClient.get(FORECAST_URL) {
                url {
                    parameters.append("key", BuildConfig.WEATHER_API_KEY)
                    parameters.append("q", qParam)
                    parameters.append("days", 3.toString())
                    parameters.append("lang", userLocale)
                }
            }
            if (response.status.value == 200) {
                val data = response.body<ForecastDTO>()
                data.toWeatherData()
            } else {
                null
            }
        }.getOrDefault(null)
    }

    companion object {
        private const val BASE_URL = "https://api.weatherapi.com"
        var IP_URL = "https://api.ipify.org?format=json"
        var FORECAST_URL = "$BASE_URL/v1/forecast.json"
    }
}