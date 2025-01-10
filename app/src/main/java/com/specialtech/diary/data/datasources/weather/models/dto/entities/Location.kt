package com.specialtech.diary.data.datasources.weather.models.dto.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @SerialName("country") val country: String,
    @SerialName("lat") val latitude: Double,
    @SerialName("lon") val longitude: Double,
    @SerialName("name") val name: String,
    @SerialName("region") val region: String,
    @SerialName("localtime") val localtime: String,
    @SerialName("localtime_epoch") val localtimeEpoch: Int,
    @SerialName("tz_id") val timeZone: String
)