package com.homeapps.diary.domain.models

data class HourlyWeatherModel(
    val hour: String,
    val temperature: Double,
    val pictureCode: Int?
)