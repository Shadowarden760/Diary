package com.specialtech.diary.ui.features.money

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specialtech.diary.data.datasources.money.models.MoneyData
import com.specialtech.diary.data.datasources.network.NetworkStatuses
import com.specialtech.diary.data.repositories.MoneyRepository
import com.specialtech.diary.utils.DateTimeUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoneyViewModel(private val moneyRepository: MoneyRepository): ViewModel() {
    private val _moneyState: MutableStateFlow<MoneyResult> = MutableStateFlow(
        MoneyResult.Loading(Pair(MoneyData(NetworkStatuses.UNKNOWN), MoneyData(NetworkStatuses.UNKNOWN)))
    )
    val moneyState: StateFlow<MoneyResult>
        get() = _moneyState

    fun loadMoneyData() = viewModelScope.launch {
        _moneyState.value = MoneyResult.Loading(Pair(MoneyData(NetworkStatuses.UNKNOWN), MoneyData(NetworkStatuses.UNKNOWN)))
        val responseToday = moneyRepository.getMoney(date = "")
        val responseYesterday = moneyRepository.getMoney(date = DateTimeUtils.getYesterdayDateString())
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

    sealed class MoneyResult(val moneyData: Pair<MoneyData, MoneyData>) {
        data class Loading(val data: Pair<MoneyData, MoneyData>): MoneyResult(data)
        data class Success(val data: Pair<MoneyData, MoneyData>): MoneyResult(data)
        data class Failed(val data: Pair<MoneyData, MoneyData>): MoneyResult(data)
    }

}