package com.specialtech.diary.data.datasources.weather.local

import com.specialtech.diary.data.datasources.weather.WeatherDataSource
import com.specialtech.diary.data.datasources.weather.models.FutureWeatherModel
import com.specialtech.diary.data.datasources.weather.models.HourlyWeatherModel
import com.specialtech.diary.data.datasources.weather.models.dto.IpAddressResponse
import com.specialtech.diary.data.datasources.weather.models.WeatherData

class LocalData: WeatherDataSource {
    private val hourlyWeatherModels = listOf(
        HourlyWeatherModel(hour = "11:00", temperature = "10", picturePath = "cloudy"),
        HourlyWeatherModel(hour = "12:00", temperature = "12", picturePath = "sunny"),
        HourlyWeatherModel(hour = "13:00", temperature = "14", picturePath = "rainy"),
        HourlyWeatherModel(hour = "14:00", temperature = "15", picturePath = "wind"),
        HourlyWeatherModel(hour = "14:00", temperature = "15", picturePath = "wind"),
        HourlyWeatherModel(hour = "14:00", temperature = "15", picturePath = "wind"),
        HourlyWeatherModel(hour = "14:00", temperature = "15", picturePath = "wind"),
        HourlyWeatherModel(hour = "14:00", temperature = "15", picturePath = "wind"),
        HourlyWeatherModel(hour = "15:00", temperature = "11", picturePath = "storm")
    )

    private val futureWeatherModels = listOf(
        FutureWeatherModel(date = "06.01.2025", status = "Sunny", highTemp = "25", lowTemp = "18", picturePath = "sunny"),
        FutureWeatherModel(date = "07.01.2025", status = "Cloudy", highTemp = "27", lowTemp = "17", picturePath = "cloudy"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = "21", lowTemp = "15", picturePath = "storm"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = "21", lowTemp = "15", picturePath = "storm"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = "21", lowTemp = "15", picturePath = "storm"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = "21", lowTemp = "15", picturePath = "storm"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = "21", lowTemp = "15", picturePath = "storm"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = "21", lowTemp = "15", picturePath = "storm"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = "21", lowTemp = "15", picturePath = "storm"),
        FutureWeatherModel(date = "09.01.2025", status = "Rainy", highTemp = "24", lowTemp = "17", picturePath = "rainy")
    )

    override suspend fun getIpAddress(): String? {
        return IpAddressResponse(ipAddress = "212.3.130.238").ipAddress
    }

    override suspend fun getForecast(ipAddress: String): WeatherData {
        return WeatherData(
            hourlyWeatherModel = hourlyWeatherModels,
            futureWeatherModel = futureWeatherModels
        )
    }
}