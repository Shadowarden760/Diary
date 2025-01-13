package com.specialtech.diary.data.datasources.weather.remote

import com.specialtech.diary.BuildConfig
import com.specialtech.diary.data.datasources.network.ApiClient
import com.specialtech.diary.data.datasources.weather.WeatherDataSource
import com.specialtech.diary.data.datasources.weather.models.IpAddressData
import com.specialtech.diary.data.datasources.weather.models.WeatherData
import com.specialtech.diary.data.datasources.weather.models.dto.ForecastResponse
import com.specialtech.diary.data.datasources.weather.models.dto.IpAddressResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class WeatherApiService(private val apiClient: ApiClient): WeatherDataSource {

    override suspend fun getIpAddress(): IpAddressData {
        val response = apiClient.httpClient.get(IP_URL)
        return if (response.status.value == 200) {
            val ip = response.body<IpAddressResponse>().ipAddress
            IpAddressData(ip = ip)
        } else {
            IpAddressData(ip = null, errorMessage = response.bodyAsText())
        }
    }

    override suspend fun getForecast(ipAddress: String, userLocale: String): WeatherData {
        val response = apiClient.httpClient.get(FORECAST_URL) {
            url {
                parameters.append("key", BuildConfig.WEATHER_API_KEY)
                parameters.append("q", ipAddress)
                parameters.append("days", 3.toString())
                parameters.append("lang", userLocale)
            }
        }
        return if (response.status.value == 200) {
            val data = response.body<ForecastResponse>()
            data.toWeatherData()
        } else {
            WeatherData()
        }
    }

    companion object {
        private const val BASE_URL: String = "https://api.weatherapi.com"
        var IP_URL = "https://api.ipify.org?format=json"
        var FORECAST_URL = "$BASE_URL/v1/forecast.json"
    }

}