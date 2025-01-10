package com.specialtech.diary.ui.features.weather.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.specialtech.diary.R
import com.specialtech.diary.data.datasources.weather.models.FutureWeatherModel
import com.specialtech.diary.data.datasources.weather.models.HourlyWeatherModel
import com.specialtech.diary.data.datasources.weather.models.WeatherData
import com.specialtech.diary.ui.features.weather.WeatherViewModel
import com.specialtech.diary.ui.theme.MainOrange

@Composable
fun Forecast(forecastResult: WeatherViewModel.ForecastResult) {
    var isVisible = false
    val weatherData = when(forecastResult) {
        is WeatherViewModel.ForecastResult.Success -> {
            isVisible = true
            forecastResult.data
        }
        else -> {
            WeatherData()
        }
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = weatherData.weatherStatus,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    textAlign = TextAlign.Center
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(weatherData.weatherStatusImg)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp)
                )
                Text(
                    text = weatherData.dateAndTime,
                    fontSize = 19.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = weatherData.currentTemperature,
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
                            icon = R.drawable.rain,
                            value = weatherData.rainPct,
                            label = "Rain"
                        )
                        WeatherDetailedItem(
                            icon = R.drawable.wind,
                            value = weatherData.windSpeed,
                            label = "Wind Speed"
                        )
                        WeatherDetailedItem(
                            icon = R.drawable.humidity,
                            value = weatherData.humidityPct,
                            label = "Humidity"
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Today",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(weatherData.hourlyWeatherModel) { item ->
                        HourlyWeatherItem(item)
                    }
                }
            }

            item {
                Text(
                    text = "Future",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }

            items(weatherData.futureWeatherModel) { item ->
                FutureWeatherItem(item)
            }
        }
    }
}

@Composable
fun WeatherDetailedItem(icon: Int, value: String, label: String) {
    Column(modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(icon),
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

@Composable
fun HourlyWeatherItem(hourlyWeather: HourlyWeatherModel) {
    Column(
        modifier = Modifier
            .width(90.dp)
            .wrapContentHeight()
            .padding(4.dp)
            .background(color = MainOrange, shape = RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = hourlyWeather.hour,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(hourlyWeather.picturePath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(65.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = hourlyWeather.temperature,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FutureWeatherItem(futureWeather: FutureWeatherModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = futureWeather.date,
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
            text = "${futureWeather.highTemp}/${futureWeather.lowTemp}",
            color = Color.White,
            fontSize = 14.sp
        )
    }
}
