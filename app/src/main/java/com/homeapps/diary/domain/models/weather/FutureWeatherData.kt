package com.homeapps.diary.domain.models.weather

data class FutureWeatherData(
    val date: String,
    val pictureCode: Int?,
    val status: String,
    val highTemp: Double,
    val lowTemp: Double
)