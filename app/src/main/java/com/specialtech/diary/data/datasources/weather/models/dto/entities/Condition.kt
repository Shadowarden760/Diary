package com.specialtech.diary.data.datasources.weather.models.dto.entities

import kotlinx.serialization.Serializable

@Serializable
data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)