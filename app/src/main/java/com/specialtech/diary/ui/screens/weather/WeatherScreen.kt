package com.specialtech.diary.ui.screens.weather

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.specialtech.diary.R
import com.specialtech.diary.data.datasources.weather.models.FutureWeatherModel
import com.specialtech.diary.data.datasources.weather.models.HourlyWeatherModel
import com.specialtech.diary.ui.theme.MainOrange
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun WeatherScreen(
    innerPaddingValues: PaddingValues = PaddingValues(8.dp),
    viewModel: WeatherViewModel = koinViewModel()
) {
    val hourlyWeather = viewModel.hourlyWeather.collectAsStateWithLifecycle()
    val futureWeather = viewModel.futureWeather.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadWeather()
    }

    Column(modifier = Modifier.fillMaxSize().padding(innerPaddingValues)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Some text",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = painterResource(R.drawable.cloudy_sunny),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp)
                )
                Text(
                    text = "Mon Jun | 10:00 AM",
                    fontSize = 19.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "25°",
                    fontSize = 63.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Gagarin",
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
                            value = "22%",
                            label = "Rain"
                        )
                        WeatherDetailedItem(
                            icon = R.drawable.wind,
                            value = "22%",
                            label = "Wind Speed"
                        )
                        WeatherDetailedItem(
                            icon = R.drawable.humidity,
                            value = "18%",
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
                LazyRow(modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(hourlyWeather.value) { item ->
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

            items(futureWeather.value) { item ->
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
            modifier = Modifier.size(34.dp)
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
        Image(
            painter = painterResource(getDrawableResource(hourlyWeather.picturePath)),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "${hourlyWeather.temperature}°",
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
            text = futureWeather.day,
            color = Color.White,
            fontSize = 14.sp
        )
        Image(
            painter = painterResource(getDrawableResource(futureWeather.picturePath)),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 32.dp)
                .size(45.dp)
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

fun getDrawableResource(label: String): Int {
    return when(label) {
        "cloudy" -> R.drawable.cloudy
        "sunny" -> R.drawable.sunny
        "rainy" -> R.drawable.rainy
        "wind" -> R.drawable.wind
        "storm" -> R.drawable.storm
        else -> android.R.drawable.ic_menu_help
    }
}