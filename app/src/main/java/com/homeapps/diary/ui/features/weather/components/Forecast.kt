package com.homeapps.diary.ui.features.weather.components

import android.location.Location
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.homeapps.diary.R
import com.homeapps.diary.domain.models.weather.WeatherData
import io.github.dellisd.spatialk.geojson.Position
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState

@Composable
fun Forecast(
    weatherData: WeatherData,
    userLocation: Location?,
    innerPadding: PaddingValues,
) {
    val cameraState = rememberCameraState()
    val isMapMoving = remember { mutableStateOf(false) }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        userScrollEnabled = !isMapMoving.value,
        contentPadding = innerPadding,
        modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)
    ) {
        item { ForecastCurrent(weatherData = weatherData) }
        item {
            Text(
                text = stringResource(R.string.weather_text_today),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            )
        }
        item { ForecastToday(hourlyData = weatherData.hourlyWeatherData) }
        item {
            Text(
                text = stringResource(R.string.weather_text_future),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            )
        }
        items(weatherData.futureWeatherData) { item ->
            FutureWeatherItem(item)
        }
        if (userLocation != null) {
            val userPosition = Position(
                longitude = userLocation.longitude,
                latitude = userLocation.latitude
            )
            cameraState.position = CameraPosition(target = userPosition, zoom = 12.0)
            item {
                DiaryMap(
                    userPosition = userPosition,
                    cameraState = cameraState,
                    onMapTouch = { moving -> isMapMoving.value = moving}
                )
            }
        }
    }
}