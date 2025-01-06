package com.specialtech.diary.data.datasources.weather.local

import com.specialtech.diary.data.datasources.weather.WeatherDataSource
import com.specialtech.diary.data.datasources.weather.models.FutureWeatherModel
import com.specialtech.diary.data.datasources.weather.models.HourlyWeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class LocalData: WeatherDataSource {
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

    override fun getHourlyWeather(): Flow<List<HourlyWeatherModel>> {
        return flowOf(hourlyWeatherModels)
    }

    override fun getFutureWeather(): Flow<List<FutureWeatherModel>> {
        return flowOf(futureWeatherModels)
    }
}