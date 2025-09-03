package com.homeapps.diary.data.datasources.weather.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IpAddressDTO(
    @SerialName("ip") val ipAddress: String
)