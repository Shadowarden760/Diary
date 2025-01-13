package com.specialtech.diary.data.datasources.money.models.dto

import com.specialtech.diary.data.datasources.money.models.MoneyData
import com.specialtech.diary.data.datasources.money.models.MoneyItemData
import com.specialtech.diary.data.datasources.network.NetworkStatuses
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class MoneyResponseDTO(
    val success: Boolean,
    val date: String,
    val timestamp: Int,
    val base: String,
    val rates: JsonObject,
) {

    fun toMoneyData(): MoneyData {
        val moneyRates: MutableList<MoneyItemData> = mutableListOf()
        rates.keys.forEach { key ->
            val value = rates.getValue(key)
            moneyRates.add(MoneyItemData(name = key, description = "desc", rate = value.toString().toDouble()))
        }
        return MoneyData(
            status = NetworkStatuses.SUCCESS,
            date = date,
            moneyRates = moneyRates
        )
    }

}