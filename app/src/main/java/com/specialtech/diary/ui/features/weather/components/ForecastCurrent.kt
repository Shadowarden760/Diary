package com.specialtech.diary.ui.features.weather.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.specialtech.diary.R
import com.specialtech.diary.data.datasources.weather.models.WeatherData
import com.specialtech.diary.ui.theme.MainOrange
import com.specialtech.diary.utils.DateTimeUtils
import compose.icons.WeatherIcons
import compose.icons.weathericons.Humidity
import compose.icons.weathericons.Umbrella
import compose.icons.weathericons.Windy

@Composable
fun ForecastCurrent(weatherData: WeatherData) {
    Text(
        text = weatherData.weatherStatus,
        fontSize = 20.sp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        textAlign = TextAlign.Center
    )
    Image(
        imageVector = WEATHER_ICONS[weatherData.weatherStatusCode] ?: Icons.Default.Warning,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp)
    )
    Text(
        text = DateTimeUtils.formatDate(
            dateString = weatherData.dateAndTime
        ),
        fontSize = 19.sp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        textAlign = TextAlign.Center
    )
    Text(
        text = "${weatherData.currentTemperature}°",
        fontSize = 63.sp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        textAlign = TextAlign.Center
    )
    Text(
        text = weatherData.region,
        fontSize = 20.sp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        textAlign = TextAlign.Center
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .background(
                color = MainOrange,
                shape = RoundedCornerShape(25.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
    Column(modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.white),
            textAlign = TextAlign.Center
        )
        Text(
            text = label,
            color = colorResource(R.color.white),
            textAlign = TextAlign.Center
        )
    }
}

