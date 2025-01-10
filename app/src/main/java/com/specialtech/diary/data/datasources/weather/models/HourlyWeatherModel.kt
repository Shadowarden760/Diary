package com.specialtech.diary.data.datasources.weather.models

data class HourlyWeatherModel(
    val hour: String,
    val temperature: String,
    val picturePath: String
)