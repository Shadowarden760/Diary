package com.specialtech.diary.ui.features.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.specialtech.diary.ui.features.weather.components.Forecast
import com.specialtech.diary.ui.features.weather.components.Waiting
import com.specialtech.diary.ui.features.weather.components.WeatherError
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = koinViewModel(),
    goHome: () -> Unit = {}
) {
    val forecast = viewModel.forecast.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadWeather(Locale.current.language)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Waiting(
            isVisible = forecast.value is WeatherViewModel.ForecastResult.Loading
        )
        WeatherError(
            isVisible = forecast.value is WeatherViewModel.ForecastResult.Failure,
            errorMessage = if (forecast.value is WeatherViewModel.ForecastResult.Failure) (forecast.value as WeatherViewModel.ForecastResult.Failure).message else "",
            tryAgain = { viewModel.loadWeather(Locale.current.language) },
            goHome = goHome
        )
        Forecast(
            isVisible = forecast.value is WeatherViewModel.ForecastResult.Success,
            forecastResult = forecast.value
        )
    }
}
