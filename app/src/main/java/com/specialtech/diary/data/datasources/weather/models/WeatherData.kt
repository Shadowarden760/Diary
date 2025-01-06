package com.specialtech.diary.data.datasources.weather.models

data class WeatherData(
    val hourlyWeatherModel: List<HourlyWeatherModel>,
    val futureWeatherModel: List<FutureWeatherModel>
)
