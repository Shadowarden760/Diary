package com.homeapps.diary.domain.models.weather

data class HourlyWeatherData(
    val hour: String,
    val temperature: Double,
    val pictureCode: Int?
)