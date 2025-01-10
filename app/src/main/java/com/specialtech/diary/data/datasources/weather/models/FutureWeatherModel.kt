package com.specialtech.diary.data.datasources.weather.models

data class FutureWeatherModel(
    val date: String,
    val picturePath: String,
    val status: String,
    val highTemp: String,
    val lowTemp: String
)