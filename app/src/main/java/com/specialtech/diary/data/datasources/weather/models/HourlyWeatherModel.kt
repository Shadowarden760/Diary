package com.specialtech.diary.data.datasources.weather.models

data class HourlyWeatherModel(
    val hour: String,
    val temperature: Int,
    val picturePath: String
)