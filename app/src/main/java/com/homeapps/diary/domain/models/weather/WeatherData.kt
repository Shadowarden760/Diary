package com.homeapps.diary.domain.models.weather

data class WeatherData(
    val weatherStatus: String = "",
    val weatherStatusCode: Int? = null,
    val dateAndTime: String = "",
    val currentTemperature: Double = 0.0,
    val region: String = "",
    val rainPct: Int = 0,
    val windSpeed: Double = 0.0,
    val humidityPct: Int = 0,
    val hourlyWeatherData: List<HourlyWeatherData> = emptyList(),
    val futureWeatherData: List<FutureWeatherData> = emptyList()
)
