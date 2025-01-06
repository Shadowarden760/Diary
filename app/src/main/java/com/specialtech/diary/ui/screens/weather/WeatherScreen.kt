package com.specialtech.diary.ui.screens.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.specialtech.diary.ui.screens.weather.components.Forecast
import com.specialtech.diary.ui.screens.weather.components.Waiting
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun WeatherScreen(
    innerPaddingValues: PaddingValues = PaddingValues(8.dp),
    viewModel: WeatherViewModel = koinViewModel()
) {
    val forecast = viewModel.forecast.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadWeather()
    }

    Column(modifier = Modifier.fillMaxSize().padding(innerPaddingValues)) {
        Waiting(isVisible = forecast.value !is WeatherViewModel.ForecastResult.Success)
        Forecast(forecastResult = forecast.value)
    }
}
