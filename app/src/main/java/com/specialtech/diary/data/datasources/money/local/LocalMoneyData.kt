package com.specialtech.diary.data.datasources.money.local

import com.specialtech.diary.data.datasources.money.MoneyDataSource
import com.specialtech.diary.data.datasources.money.models.MoneyData
import com.specialtech.diary.data.datasources.money.models.MoneyItemData
import com.specialtech.diary.data.datasources.network.NetworkStatuses

class LocalMoneyData: MoneyDataSource {
    val moneyData1 = MoneyData(
        status = NetworkStatuses.SUCCESS,
        errorMessage = "",
        date = "2025-01-10",
        moneyRates = listOf(
            MoneyItemData(name = "EUR", description = "desc", rate = 1.0),
            MoneyItemData(name = "USD", description = "desc", rate = 0.95),
            MoneyItemData(name = "RUB", description = "desc", rate = 102.0)
        )
    )

    val moneyData2 = MoneyData(
        status = NetworkStatuses.SUCCESS,
        errorMessage = "",
        date = "2024-01-11",
        moneyRates = listOf(
            MoneyItemData(name = "EUR", description = "desc", rate = 1.0),
            MoneyItemData(name = "USD", description = "desc", rate = 0.97),
            MoneyItemData(name = "RUB", description = "desc", rate = 115.0),
        )
    )

    override suspend fun getMoneyRates(date: String, base: String): MoneyData {
        return listOf(moneyData1, moneyData2).shuffled().first()
    }

}