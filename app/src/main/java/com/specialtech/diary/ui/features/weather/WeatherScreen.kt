package com.specialtech.diary.ui.features.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.specialtech.diary.ui.features.weather.components.Forecast
import com.specialtech.diary.ui.features.weather.components.Waiting
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = koinViewModel()
) {
    val forecast = viewModel.forecast.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadWeather()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Waiting(isVisible = forecast.value !is WeatherViewModel.ForecastResult.Success)
        Forecast(forecastResult = forecast.value)
    }
}
