package com.specialtech.diary.ui.features.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.specialtech.diary.data.datasources.weather.models.HourlyWeatherModel

@Composable
fun ForecastToday(hourlyData: List<HourlyWeatherModel>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(hourlyData) { item ->
            HourlyWeatherItem(item)
        }
    }
}
