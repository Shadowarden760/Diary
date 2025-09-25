package com.homeapps.diary.ui.features.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.homeapps.diary.domain.models.weather.HourlyWeatherData

@Composable
fun ForecastToday(hourlyData: List<HourlyWeatherData>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(hourlyData) { index, item ->
            HourlyWeatherItem(itemIndex = index, hourlyWeather = item)
        }
    }
}