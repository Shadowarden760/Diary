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
import com.specialtech.diary.ui.features.weather.components.WeatherError
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = koinViewModel(),
    goHome: () -> Unit = {}
) {
    val forecast = viewModel.forecast.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadWeather()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Waiting(
            isVisible = forecast.value is WeatherViewModel.ForecastResult.Loading
        )
        WeatherError(
            isVisible = forecast.value is WeatherViewModel.ForecastResult.Failure,
            forecastResult = forecast.value,
            tryAgain = { viewModel.loadWeather() },
            goHome = goHome
        )
        Forecast(
            isVisible = forecast.value is WeatherViewModel.ForecastResult.Success,
            forecastResult = forecast.value
        )
    }
}
