package com.specialtech.diary.ui.screens.weather.models

data class HourlyWeatherModel(
    val hour: String,
    val temperature: Int,
    val picturePath: String
)