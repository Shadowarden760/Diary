package com.homeapps.diary.data.datasources.weather.local

import com.homeapps.diary.domain.models.weather.FutureWeatherData
import com.homeapps.diary.domain.models.weather.HourlyWeatherData
import com.homeapps.diary.domain.models.weather.IpAddressData
import com.homeapps.diary.domain.models.weather.WeatherData

class LocalWeatherApi {
    private val hourlyWeatherData = listOf(
        HourlyWeatherData(hour = "11:00", temperature = 10.0, pictureCode = null),
        HourlyWeatherData(hour = "12:00", temperature = 12.0, pictureCode = null),
        HourlyWeatherData(hour = "13:00", temperature = 14.0, pictureCode = null),
        HourlyWeatherData(hour = "14:00", temperature = 15.0, pictureCode = null),
        HourlyWeatherData(hour = "14:00", temperature = 15.0, pictureCode = null),
        HourlyWeatherData(hour = "14:00", temperature = 15.0, pictureCode = null),
        HourlyWeatherData(hour = "14:00", temperature = 5.0, pictureCode = null),
        HourlyWeatherData(hour = "14:00", temperature = 1.0, pictureCode = null),
        HourlyWeatherData(hour = "15:00", temperature = 11.0, pictureCode = null)
    )

    private val futureWeatherData = listOf(
        FutureWeatherData(date = "06.01.2025", status = "Sunny", highTemp = 25.0, lowTemp = 18.0, pictureCode = null),
        FutureWeatherData(date = "07.01.2025", status = "Cloudy", highTemp = 27.0, lowTemp = 17.0, pictureCode = null),
        FutureWeatherData(date = "08.01.2025", status = "Storm", highTemp = 21.0, lowTemp = 15.0, pictureCode = null),
        FutureWeatherData(date = "08.01.2025", status = "Storm", highTemp = 21.0, lowTemp = 15.0, pictureCode = null),
        FutureWeatherData(date = "08.01.2025", status = "Storm", highTemp = 21.0, lowTemp = 15.0, pictureCode = null),
        FutureWeatherData(date = "08.01.2025", status = "Storm", highTemp = 21.0, lowTemp = 15.0, pictureCode = null),
        FutureWeatherData(date = "08.01.2025", status = "Storm", highTemp = 21.0, lowTemp = 15.0, pictureCode = null),
        FutureWeatherData(date = "08.01.2025", status = "Storm", highTemp = 2.0, lowTemp = 15.0, pictureCode = null),
        FutureWeatherData(date = "08.01.2025", status = "Storm", highTemp = 21.0, lowTemp = 15.0, pictureCode = null),
        FutureWeatherData(date = "09.01.2025", status = "Rainy", highTemp = 24.0, lowTemp = 17.0, pictureCode = null)
    )

    fun getIpAddress(): IpAddressData {
        return IpAddressData(ip = "8.8.8.8")
    }

    fun getForecast(qParam: String, userLocale: String): WeatherData? {
        return WeatherData(
            hourlyWeatherData = hourlyWeatherData,
            futureWeatherData = futureWeatherData
        )
    }
}