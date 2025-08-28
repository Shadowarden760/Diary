package com.homeapps.diary.data.datasources.weather.remote

import com.homeapps.diary.BuildConfig
import com.homeapps.diary.data.datasources.network.ApiClient
import com.homeapps.diary.data.datasources.weather.WeatherDataSource
import com.homeapps.diary.data.datasources.weather.models.IpAddressData
import com.homeapps.diary.data.datasources.weather.models.WeatherData
import com.homeapps.diary.data.datasources.weather.models.dto.ForecastResponse
import com.homeapps.diary.data.datasources.weather.models.dto.IpAddressResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class WeatherApiService(private val apiClient: ApiClient): WeatherDataSource {

    override suspend fun getIpAddress(): IpAddressData {
        return runCatching {
            val response = apiClient.httpClient.get(IP_URL)
            if (response.status.value == 200) {
                val ip = response.body<IpAddressResponse>().ipAddress
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
                val data = response.body<ForecastResponse>()
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