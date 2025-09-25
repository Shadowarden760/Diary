package com.homeapps.diary.ui.features.weather.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homeapps.diary.domain.models.weather.HourlyWeatherData
import com.homeapps.diary.utils.DateTimeUtils

@Composable
fun HourlyWeatherItem(
    itemIndex: Int,
    hourlyWeather: HourlyWeatherData
) {
    val startPadding = if (itemIndex == 0) 24.dp else 0.dp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(75.dp + startPadding)
            .wrapContentHeight()
            .padding(start = startPadding)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.large
            )
    ) {
        Text(
            text = DateTimeUtils.formatDate(dateString = hourlyWeather.hour, returnFormat = "HH:mm"),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Image(
            imageVector = WEATHER_ICONS[hourlyWeather.pictureCode] ?: Icons.Default.Warning,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .size(65.dp)
                .padding(8.dp)
        )
        Text(
            text = "${hourlyWeather.temperature}°",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}