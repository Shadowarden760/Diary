package com.specialtech.diary.data.datasources.money.models

import com.specialtech.diary.data.datasources.network.NetworkStatuses

data class MoneyData(
    val status: NetworkStatuses,
    val errorMessage: String = "",
    val date: String = "",
    var moneyRates: List<MoneyItemData> = emptyList()
)