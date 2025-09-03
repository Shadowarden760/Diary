package com.homeapps.diary.data.datasources.weather.models.dto

import com.homeapps.diary.data.datasources.weather.models.dto.others.CurrentDTO
import com.homeapps.diary.data.datasources.weather.models.dto.others.ForecastDTO
import com.homeapps.diary.data.datasources.weather.models.dto.others.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDTO(
    @SerialName("current") val currentForecast: CurrentDTO,
    @SerialName("forecast") val fullForecast: ForecastDTO,
    @SerialName("location") val location: Location
)