package com.specialtech.diary.ui.features.weather.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.specialtech.diary.data.datasources.weather.models.FutureWeatherModel
import com.specialtech.diary.utils.DateTimeUtils

@Composable
fun FutureWeatherItem(futureWeather: FutureWeatherModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = DateTimeUtils.formatDate(
                dateString = futureWeather.date,
                dateFormat = "yyyy-MM-dd",
                returnFormat = "dd-MM-yyyy"
            ),
            style = MaterialTheme.typography.bodyMedium
        )
        Image(
            imageVector = WEATHER_ICONS[futureWeather.pictureCode] ?: Icons.Default.Warning,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .size(65.dp)
                .padding(8.dp)
        )
        Text(
            text = futureWeather.status,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Text(
            text = "${futureWeather.highTemp}°/${futureWeather.lowTemp}°",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}