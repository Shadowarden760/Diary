package com.specialtech.diary.data.datasources.weather.models

data class HourlyWeatherModel(
    val hour: String,
    val temperature: Double,
    val picturePath: String
)