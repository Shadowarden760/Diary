package com.specialtech.diary.ui.features.money

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.specialtech.diary.ui.features.money.components.MoneyError
import com.specialtech.diary.ui.features.money.components.MoneyRates
import com.specialtech.diary.ui.features.money.components.MoneyWaiting
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoneyScreen(
    viewModel: MoneyViewModel = koinViewModel(),
    goHome: () -> Unit
) {
    val moneyState = viewModel.moneyState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadMoneyData(base = "EUR")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MoneyWaiting(
            isVisible = moneyState.value is MoneyViewModel.MoneyResult.Loading
        )
        MoneyError(
            isVisible = moneyState.value is MoneyViewModel.MoneyResult.Failed,
            errorMessage = moneyState.value.moneyData.first.errorMessage,
            tryAgain = {
                viewModel.loadMoneyData(base = "EUR")
            },
            goHome = goHome
        )
        MoneyRates(
            isVisible = moneyState.value is MoneyViewModel.MoneyResult.Success,
            moneyData = moneyState.value.moneyData,
            convertMoneyData = viewModel::convertMoneyData
        )
    }
}