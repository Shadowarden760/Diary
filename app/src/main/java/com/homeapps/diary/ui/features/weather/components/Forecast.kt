package com.homeapps.diary.ui.features.weather.components

import android.location.Location
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.homeapps.diary.R
import com.homeapps.diary.domain.models.weather.WeatherData
import org.maplibre.compose.map.MaplibreMap

fun Modifier.onPointerInteractionStartEnd(
    onPointerStart: () -> Unit,
    onPointerEnd: () -> Unit,
) = pointerInput(onPointerStart, onPointerEnd) {
    awaitEachGesture {
        awaitFirstDown(requireUnconsumed = false)
        onPointerStart()
        do {
            val event = awaitPointerEvent()
        } while (event.changes.any { it.pressed })
        onPointerEnd()
    }
}

@Composable
fun Forecast(weatherData: WeatherData, userLocation: Location?) {
    val isMapMoving = remember { mutableStateOf(false) }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        userScrollEnabled = !isMapMoving.value,
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
        item { ForecastToday(hourlyData = weatherData.hourlyWeatherData) }
        item {
            Text(
                text = stringResource(R.string.weather_text_future),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 8.dp)
            )
        }
        items(weatherData.futureWeatherData) { item ->
            FutureWeatherItem(item)
        }
        if (userLocation != null) {
            item {
                MaplibreMap(
                    modifier = Modifier
                        .onPointerInteractionStartEnd(
                            onPointerStart = { isMapMoving.value = true },
                            onPointerEnd = { isMapMoving.value = false }
                        )
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 8.dp)
                )
            }
        }
    }
}