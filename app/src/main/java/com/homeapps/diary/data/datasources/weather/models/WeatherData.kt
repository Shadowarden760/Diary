package com.homeapps.diary.data.datasources.weather.models

data class WeatherData(
    val weatherStatus: String = "",
    val weatherStatusCode: Int? = null,
    val dateAndTime: String = "",
    val currentTemperature: Double = 0.0,
    val region: String = "",
    val rainPct: Int = 0,
    val windSpeed: Double = 0.0,
    val humidityPct: Int = 0,
    val hourlyWeatherModel: List<HourlyWeatherModel> = emptyList(),
    val futureWeatherModel: List<FutureWeatherModel> = emptyList()
)
