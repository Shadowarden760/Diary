package com.specialtech.diary.data.datasources.weather.local

import com.specialtech.diary.data.datasources.weather.WeatherDataSource
import com.specialtech.diary.data.datasources.weather.models.FutureWeatherModel
import com.specialtech.diary.data.datasources.weather.models.HourlyWeatherModel
import com.specialtech.diary.data.datasources.weather.models.IpAddress
import com.specialtech.diary.data.datasources.weather.models.WeatherData

class LocalData: WeatherDataSource {
    private val hourlyWeatherModels = listOf(
        HourlyWeatherModel(hour = "11:00", temperature = 10.0, picturePath = "cloudy"),
        HourlyWeatherModel(hour = "12:00", temperature = 12.0, picturePath = "sunny"),
        HourlyWeatherModel(hour = "13:00", temperature = 14.0, picturePath = "rainy"),
        HourlyWeatherModel(hour = "14:00", temperature = 15.0, picturePath = "wind"),
        HourlyWeatherModel(hour = "14:00", temperature = 15.0, picturePath = "wind"),
        HourlyWeatherModel(hour = "14:00", temperature = 15.0, picturePath = "wind"),
        HourlyWeatherModel(hour = "14:00", temperature = 5.0, picturePath = "wind"),
        HourlyWeatherModel(hour = "14:00", temperature = 1.0, picturePath = "wind"),
        HourlyWeatherModel(hour = "15:00", temperature = 11.0, picturePath = "storm")
    )

    private val futureWeatherModels = listOf(
        FutureWeatherModel(date = "06.01.2025", status = "Sunny", highTemp = 25.0, lowTemp = 18.0, picturePath = "sunny"),
        FutureWeatherModel(date = "07.01.2025", status = "Cloudy", highTemp = 27.0, lowTemp = 17.0, picturePath = "cloudy"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = 21.0, lowTemp = 15.0, picturePath = "storm"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = 21.0, lowTemp = 15.0, picturePath = "storm"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = 21.0, lowTemp = 15.0, picturePath = "storm"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = 21.0, lowTemp = 15.0, picturePath = "storm"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = 21.0, lowTemp = 15.0, picturePath = "storm"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = 2.0, lowTemp = 15.0, picturePath = "storm"),
        FutureWeatherModel(date = "08.01.2025", status = "Storm", highTemp = 21.0, lowTemp = 15.0, picturePath = "storm"),
        FutureWeatherModel(date = "09.01.2025", status = "Rainy", highTemp = 24.0, lowTemp = 17.0, picturePath = "rainy")
    )

    override suspend fun getIpAddress(): IpAddress {
        return IpAddress(ip = "212.3.130.238")
    }

    override suspend fun getForecast(ipAddress: String, userLocale: String): WeatherData {
        return WeatherData(
            hourlyWeatherModel = hourlyWeatherModels,
            futureWeatherModel = futureWeatherModels
        )
    }
}