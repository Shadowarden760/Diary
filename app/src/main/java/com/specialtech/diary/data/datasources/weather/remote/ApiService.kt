package com.specialtech.diary.data.datasources.weather.remote

import com.specialtech.diary.BuildConfig
import com.specialtech.diary.data.datasources.weather.WeatherDataSource
import com.specialtech.diary.data.datasources.weather.models.ForecastResponse
import com.specialtech.diary.data.datasources.weather.models.FutureWeatherModel
import com.specialtech.diary.data.datasources.weather.models.HourlyWeatherModel
import com.specialtech.diary.data.datasources.weather.models.IpAddressResponse
import com.specialtech.diary.data.datasources.weather.models.WeatherData
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(private val apiClient: ApiClient): WeatherDataSource {

    private val hourlyWeatherModels = listOf(
        HourlyWeatherModel(hour = "11:00", temperature = 10, picturePath = "cloudy"),
        HourlyWeatherModel(hour = "12:00", temperature = 12, picturePath = "sunny"),
        HourlyWeatherModel(hour = "13:00", temperature = 14, picturePath = "rainy"),
        HourlyWeatherModel(hour = "14:00", temperature = 15, picturePath = "wind"),
        HourlyWeatherModel(hour = "14:00", temperature = 15, picturePath = "wind"),
        HourlyWeatherModel(hour = "14:00", temperature = 15, picturePath = "wind"),
        HourlyWeatherModel(hour = "14:00", temperature = 15, picturePath = "wind"),
        HourlyWeatherModel(hour = "14:00", temperature = 15, picturePath = "wind"),
        HourlyWeatherModel(hour = "15:00", temperature = 11, picturePath = "storm")
    )

    private val futureWeatherModels = listOf(
        FutureWeatherModel(day = "06.01.2025", status = "Sunny", highTemp = 25, lowTemp = 18, "sunny"),
        FutureWeatherModel(day = "07.01.2025", status = "Cloudy", highTemp = 27, lowTemp = 17, "cloudy"),
        FutureWeatherModel(day = "08.01.2025", status = "Storm", highTemp = 21, lowTemp = 15, "storm"),
        FutureWeatherModel(day = "08.01.2025", status = "Storm", highTemp = 21, lowTemp = 15, "storm"),
        FutureWeatherModel(day = "08.01.2025", status = "Storm", highTemp = 21, lowTemp = 15, "storm"),
        FutureWeatherModel(day = "08.01.2025", status = "Storm", highTemp = 21, lowTemp = 15, "storm"),
        FutureWeatherModel(day = "08.01.2025", status = "Storm", highTemp = 21, lowTemp = 15, "storm"),
        FutureWeatherModel(day = "08.01.2025", status = "Storm", highTemp = 21, lowTemp = 15, "storm"),
        FutureWeatherModel(day = "08.01.2025", status = "Storm", highTemp = 21, lowTemp = 15, "storm"),
        FutureWeatherModel(day = "09.01.2025", status = "Rainy", highTemp = 24, lowTemp = 17, "rainy")
    )

    companion object {
        private const val BASE_URL: String = "https://api.weatherapi.com"
        var IP_URL = "https://api.ipify.org?format=json"
        var FORECAST_URL = "$BASE_URL/v1/forecast.json"
    }

    override suspend fun getIpAddress(): String? {
        val response = apiClient.httpClient.get(IP_URL)
        return if (response.status.value == 200) {
            response.body<IpAddressResponse>().ipAddress
        } else {
            null
        }
    }

    override suspend fun getForecast(ipAddress: String): WeatherData {
        val response = apiClient.httpClient.get(FORECAST_URL) {
            url {
                parameters.append("key", BuildConfig.WEATHER_API_KEY)
                parameters.append("q", ipAddress)
            }
        }
        return if (response.status.value == 200) {
            val data = response.body<ForecastResponse>()
            WeatherData(hourlyWeatherModels, futureWeatherModels)
        } else {
            WeatherData(emptyList(), emptyList())
        }
    }

}