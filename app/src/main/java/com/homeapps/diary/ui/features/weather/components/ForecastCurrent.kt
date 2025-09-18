package com.homeapps.diary.ui.features.weather.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.homeapps.diary.R
import com.homeapps.diary.domain.models.weather.WeatherData
import com.homeapps.diary.utils.DateTimeUtils
import compose.icons.WeatherIcons
import compose.icons.weathericons.Humidity
import compose.icons.weathericons.Umbrella
import compose.icons.weathericons.Windy

@Composable
fun ForecastCurrent(weatherData: WeatherData) {
    Text(
        text = weatherData.weatherStatus,
        style = MaterialTheme.typography.displaySmall,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
    Image(
        imageVector = WEATHER_ICONS[weatherData.weatherStatusCode] ?: Icons.Default.Warning,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .size(150.dp)
            .padding(top = 8.dp)
    )
    Text(
        text = DateTimeUtils.formatDate(dateString = weatherData.dateAndTime),
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
    Text(
        text = "${weatherData.currentTemperature}°",
        style = MaterialTheme.typography.displayLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
    Text(
        text = weatherData.region,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 16.dp, end = 24.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.extraLarge
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .padding(horizontal = 8.dp)
        ) {
            WeatherDetailedItem(
                icon = WeatherIcons.Umbrella,
                value = "${weatherData.rainPct}%",
                label = stringResource(R.string.weather_text_rain)
            )
            WeatherDetailedItem(
                icon = WeatherIcons.Windy,
                value = "${weatherData.windSpeed}${stringResource(R.string.weather_text_kmh)}",
                label = stringResource(R.string.weather_text_wind_speed)
            )
            WeatherDetailedItem(
                icon = WeatherIcons.Humidity,
                value = "${weatherData.humidityPct}%",
                label = stringResource(R.string.weather_text_humidity)
            )
        }
    }
}

@Composable
fun WeatherDetailedItem(icon: ImageVector, value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier.size(35.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}
