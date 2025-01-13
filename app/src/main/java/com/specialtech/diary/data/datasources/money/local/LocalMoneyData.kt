package com.specialtech.diary.data.datasources.money.local

import com.specialtech.diary.data.datasources.money.MoneyDataSource
import com.specialtech.diary.data.datasources.money.models.MoneyData
import com.specialtech.diary.data.datasources.money.models.MoneyItemData
import com.specialtech.diary.data.datasources.network.NetworkStatuses

class LocalMoneyData: MoneyDataSource {
    val moneyData1 = MoneyData(
        status = NetworkStatuses.SUCCESS,
        errorMessage = "",
        date = "10.01.2024",
        moneyRates = listOf(
            MoneyItemData(name = "EUR", description = "desc", rate = 102.0),
            MoneyItemData(name = "USD", description = "desc", rate = 100.0),
            MoneyItemData(name = "EUR1", description = "desc", rate = 102.0),
            MoneyItemData(name = "USD1", description = "desc", rate = 100.0),
            MoneyItemData(name = "EUR2", description = "desc", rate = 102.0),
            MoneyItemData(name = "USD2", description = "desc", rate = 100.0),
            MoneyItemData(name = "EUR3", description = "desc", rate = 102.0),
            MoneyItemData(name = "USD4", description = "desc", rate = 100.0),
        )
    )

    val moneyData2 = MoneyData(
        status = NetworkStatuses.SUCCESS,
        errorMessage = "",
        date = "10.01.2024",
        moneyRates = listOf(
            MoneyItemData(name = "EUR", description = "desc", rate = 103.0),
            MoneyItemData(name = "USD", description = "desc", rate = 99.0),
            MoneyItemData(name = "EUR1", description = "desc", rate = 102.0),
            MoneyItemData(name = "USD1", description = "desc", rate = 100.0),
            MoneyItemData(name = "EUR2", description = "desc", rate = 102.0),
            MoneyItemData(name = "USD2", description = "desc", rate = 100.0),
            MoneyItemData(name = "EUR3", description = "desc", rate = 102.0),
            MoneyItemData(name = "USD4", description = "desc", rate = 100.0),
        )
    )

    override suspend fun getMoneyRates(date: String, base: String): MoneyData {
        return listOf(moneyData1, moneyData2).shuffled().first()
    }

}