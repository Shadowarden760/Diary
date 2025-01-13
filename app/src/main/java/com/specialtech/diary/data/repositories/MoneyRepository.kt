package com.specialtech.diary.data.repositories

import com.specialtech.diary.data.datasources.money.MoneyDataSource

class MoneyRepository(private val moneyDataSource: MoneyDataSource) {

    suspend fun getMoney() = moneyDataSource.getMoneyRates(base = "RUB")

}