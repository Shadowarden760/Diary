package com.specialtech.diary.ui.features.weather.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.specialtech.diary.data.datasources.weather.models.FutureWeatherModel
import com.specialtech.diary.utils.DateTimeUtils

@Composable
fun FutureWeatherItem(futureWeather: FutureWeatherModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = DateTimeUtils.formatDate(
                dateString = futureWeather.date,
                dateFormat = "yyyy-MM-dd",
                returnFormat = "dd-MM-yyyy"
            ),
            color = Color.White,
            fontSize = 14.sp
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(futureWeather.picturePath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 32.dp)
                .size(65.dp)
        )
        Text(
            text = futureWeather.status,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${futureWeather.highTemp}°/${futureWeather.lowTemp}°",
            color = Color.White,
            fontSize = 14.sp
        )
    }
}