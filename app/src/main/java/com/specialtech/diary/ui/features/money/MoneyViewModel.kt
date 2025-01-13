package com.specialtech.diary.ui.features.money

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specialtech.diary.data.datasources.money.models.MoneyData
import com.specialtech.diary.data.datasources.network.NetworkStatuses
import com.specialtech.diary.data.repositories.MoneyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoneyViewModel(private val moneyRepository: MoneyRepository): ViewModel() {
    private val _moneyState: MutableStateFlow<MoneyResult> = MutableStateFlow(MoneyResult.Loading)
    val moneyState: StateFlow<MoneyResult>
        get() = _moneyState

    fun loadMoneyData() = viewModelScope.launch {
        val response = moneyRepository.getMoney()
        when (response.status) {
            NetworkStatuses.SUCCESS -> { _moneyState.value = MoneyResult.Success(data = response) }
            NetworkStatuses.FAILED -> { _moneyState.value = MoneyResult.Failed(data = response) }
        }
    }

    sealed class MoneyResult {
        data object Loading: MoneyResult()
        data class Success(val data: MoneyData): MoneyResult()
        data class Failed(val data: MoneyData): MoneyResult()
    }

}