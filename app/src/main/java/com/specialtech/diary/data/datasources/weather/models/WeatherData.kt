package com.specialtech.diary.data.datasources.weather.models

data class WeatherData(
    val weatherStatus: String = "",
    val weatherStatusImg: String = "",
    val dateAndTime: String = "",
    val currentTemperature: String = "",
    val region: String = "",
    val rainPct: String = "",
    val windSpeed: String = "",
    val humidityPct: String = "",
    val hourlyWeatherModel: List<HourlyWeatherModel> = emptyList(),
    val futureWeatherModel: List<FutureWeatherModel> = emptyList()
)
