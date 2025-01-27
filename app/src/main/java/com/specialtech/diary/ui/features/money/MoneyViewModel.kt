package com.specialtech.diary.ui.features.money

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specialtech.diary.data.datasources.money.models.MoneyData
import com.specialtech.diary.data.datasources.money.models.MoneyItemData
import com.specialtech.diary.data.datasources.network.NetworkStatuses
import com.specialtech.diary.data.repositories.MoneyRepository
import com.specialtech.diary.utils.DateTimeUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoneyViewModel(private val moneyRepository: MoneyRepository): ViewModel() {
    private val _moneyState: MutableStateFlow<MoneyResult> = MutableStateFlow(
        MoneyResult.Loading(Pair(MoneyData(), MoneyData()))
    )
    val moneyState: StateFlow<MoneyResult>
        get() = _moneyState

    fun loadMoneyData(base: String) = viewModelScope.launch {
        _moneyState.value = MoneyResult.Loading(Pair(MoneyData(), MoneyData()))
        val responseToday = moneyRepository.getMoney(date = "", base = base)
        val responseYesterday = moneyRepository.getMoney(date = DateTimeUtils.getYesterdayDateString(), base = base)
        when {
            responseToday.status == NetworkStatuses.SUCCESS && responseYesterday.status == NetworkStatuses.SUCCESS -> {
                _moneyState.value = MoneyResult.Success(Pair(responseYesterday, responseToday))
            }
            responseToday.status == NetworkStatuses.FAILED || responseYesterday.status == NetworkStatuses.FAILED -> {
                _moneyState.value = MoneyResult.Failed(Pair(responseYesterday, responseToday))
            }
            else -> { /* ignore */ }
        }
    }

    fun convertMoneyData(base: String, target: String, data: MoneyData,
                         allMoneyRates: Array<String>, allMoneyDes: Array<String>): MoneyData {
        val rateCoeff = (data.moneyRates.firstOrNull { it.name == target })?.rate ?: 0.0
        var newMoneyRates: MutableList<MoneyItemData> = mutableListOf()
        data.moneyRates.forEach { rateItem ->
            val newRate = if (rateItem.name != base && rateItem.name != target) {
                (rateCoeff / rateItem.rate * 100).toInt() / 100.0
            } else {
                (rateCoeff * 100).toInt() / 100.0
            }
            val itemIndex = allMoneyRates.indexOf(rateItem.name)
            if (itemIndex != -1) {
                newMoneyRates.add(
                    MoneyItemData(
                        name = rateItem.name,
                        rate = newRate,
                        description = allMoneyDes[itemIndex]
                    )
                )
            }
        }
        newMoneyRates = newMoneyRates.filter { it.name != target }.toMutableList()
        val result = MoneyData(
            status = data.status,
            errorMessage = data.errorMessage,
            date = data.date,
            moneyRates = newMoneyRates
        )
        return result
    }

    sealed class MoneyResult(val moneyData: Pair<MoneyData, MoneyData>) {
        data class Loading(val data: Pair<MoneyData, MoneyData>): MoneyResult(data)
        data class Success(val data: Pair<MoneyData, MoneyData>): MoneyResult(data)
        data class Failed(val data: Pair<MoneyData, MoneyData>): MoneyResult(data)
    }

}