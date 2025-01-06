package com.specialtech.diary.data.datasources.weather.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    @SerialName("current") val currentForecast: Current,
    @SerialName("forecast") val fullForecast: Forecast,
    @SerialName("location") val location: Location
)