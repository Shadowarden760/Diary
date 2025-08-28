package com.homeapps.diary.data.datasources.weather.models

data class HourlyWeatherModel(
    val hour: String,
    val temperature: Double,
    val pictureCode: Int?
)