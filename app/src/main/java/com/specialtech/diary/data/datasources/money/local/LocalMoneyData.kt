package com.specialtech.diary.data.datasources.money.local

import com.specialtech.diary.data.datasources.money.MoneyDataSource
import com.specialtech.diary.data.datasources.money.models.MoneyData
import com.specialtech.diary.data.datasources.money.models.MoneyItemData
import com.specialtech.diary.data.datasources.network.NetworkStatuses

class LocalMoneyData: MoneyDataSource {
    private val moneyData = MoneyData(
        status = NetworkStatuses.SUCCESS,
        errorMessage = "",
        date = "10.01.2024",
        moneyRates = listOf(
            MoneyItemData(name = "EUR", description = "desc", rate = 102.0),
            MoneyItemData(name = "USD", description = "desc", rate = 100.0)
        )
    )

    override suspend fun getMoneyRates(base: String): MoneyData {
        return moneyData
    }

}