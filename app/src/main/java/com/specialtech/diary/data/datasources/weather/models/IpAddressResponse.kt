package com.specialtech.diary.data.datasources.weather.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IpAddressResponse(
    @SerialName("ip") val ipAddress: String
)