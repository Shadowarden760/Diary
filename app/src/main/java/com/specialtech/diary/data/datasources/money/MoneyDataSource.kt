package com.specialtech.diary.data.datasources.money

import com.specialtech.diary.data.datasources.money.models.MoneyData

interface MoneyDataSource {

    suspend fun getMoneyRates(date: String, base: String): MoneyData

}