package com.specialtech.diary.data.datasources.weather.models

import kotlinx.serialization.Serializable

@Serializable
data class Forecastday(
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>
)