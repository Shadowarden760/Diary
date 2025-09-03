package com.homeapps.diary.data.datasources.weather.models.dto.others

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDayDTO(
    @SerialName("date") val date: String,
    @SerialName("date_epoch") val dateEpoch: Int,
    @SerialName("day") val day: DayDTO,
    @SerialName("hour") val hour: List<HourDTO>
)