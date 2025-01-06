package com.specialtech.diary.ui.screens.weather.models

data class FutureWeatherModel(
    val day: String,
    val status: String,
    val highTemp: Int,
    val lowTemp: Int,
    val picturePath: String
)