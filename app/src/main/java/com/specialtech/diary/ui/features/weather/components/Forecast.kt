package com.specialtech.diary.ui.features.weather.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.specialtech.diary.R
import com.specialtech.diary.data.datasources.weather.models.WeatherData
import com.specialtech.diary.ui.features.weather.WeatherViewModel

@Composable
fun Forecast(isVisible: Boolean, forecastResult: WeatherViewModel.ForecastResult) {
    val weatherData = when(forecastResult) {
        is WeatherViewModel.ForecastResult.Success -> { forecastResult.data }
        else -> { WeatherData() }
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            item { ForecastCurrent(weatherData = weatherData) }

            item {
                Text(
                    text = stringResource(R.string.weather_text_today),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 8.dp)
                )
            }

            item { ForecastToday(hourlyData = weatherData.hourlyWeatherModel) }

            item {
                Text(
                    text = stringResource(R.string.weather_text_future),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 8.dp)
                )
            }

            items(weatherData.futureWeatherModel) { item ->
                FutureWeatherItem(item)
            }
        }
    }
}
