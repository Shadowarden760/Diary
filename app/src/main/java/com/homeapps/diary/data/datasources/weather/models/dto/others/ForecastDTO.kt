package com.homeapps.diary.data.datasources.weather.models.dto.others

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDTO(
    @SerialName("forecastday") val forecastDay: List<ForecastDayDTO>
)