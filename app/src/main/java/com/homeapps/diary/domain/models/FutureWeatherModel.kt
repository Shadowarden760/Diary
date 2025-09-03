package com.homeapps.diary.domain.models

data class FutureWeatherModel(
    val date: String,
    val pictureCode: Int?,
    val status: String,
    val highTemp: Double,
    val lowTemp: Double
)