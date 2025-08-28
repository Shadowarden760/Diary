package com.homeapps.diary.data.datasources.weather.models.dto.entities

import kotlinx.serialization.Serializable

@Serializable
data class Forecastday(
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>
)