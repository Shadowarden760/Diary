package com.specialtech.diary.data.datasources.weather.models.dto.entities

import kotlinx.serialization.Serializable

@Serializable
data class Forecast(
    val forecastday: List<Forecastday>
)