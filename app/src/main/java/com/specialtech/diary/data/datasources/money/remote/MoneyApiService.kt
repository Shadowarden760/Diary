package com.specialtech.diary.data.datasources.money.remote

import com.specialtech.diary.BuildConfig
import com.specialtech.diary.data.datasources.money.MoneyDataSource
import com.specialtech.diary.data.datasources.money.models.MoneyData
import com.specialtech.diary.data.datasources.money.models.dto.MoneyResponseDTO
import com.specialtech.diary.data.datasources.network.ApiClient
import com.specialtech.diary.data.datasources.network.NetworkStatuses
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class MoneyApiService(private val apiClient: ApiClient): MoneyDataSource {

    override suspend fun getMoneyRates(base: String): MoneyData {
        val response = apiClient.httpClient.get(MONEY_URL) {
            url {
                parameters.append("access_key", BuildConfig.MONEY_API_KEY)
                parameters.append("base", base)
            }
        }
        return if (response.status.value == 200) {
            val data = response.body<MoneyResponseDTO>()
            data.toMoneyData()
        } else {
            MoneyData(status = NetworkStatuses.FAILED, errorMessage = response.bodyAsText())
        }
    }

    companion object {
        private const val BASE_URL: String = "https://api.exchangeratesapi.io"
        var MONEY_URL = "$BASE_URL/v1/latest"
    }

}
