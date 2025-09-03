package com.homeapps.diary.data.mappers

import com.homeapps.diary.data.datasources.weather.models.dto.ForecastDTO
import com.homeapps.diary.domain.models.FutureWeatherModel
import com.homeapps.diary.domain.models.HourlyWeatherModel
import com.homeapps.diary.domain.models.WeatherData

fun ForecastDTO.toWeatherData(): WeatherData {
    val hourlyData: MutableList<HourlyWeatherModel> = mutableListOf()
    val futureData: MutableList<FutureWeatherModel> = mutableListOf()
    fullForecast.forecastDay.firstOrNull()?.let {
        it.hour.forEach { hourData ->
            val data = HourlyWeatherModel(
                hour = hourData.time,
                temperature = hourData.tempC,
                pictureCode = hourData.condition.code
            )
            hourlyData.add(data)
        }
    }
    fullForecast.forecastDay.forEach { future ->
        val data = FutureWeatherModel(
            date = future.date,
            pictureCode = future.day.condition.code,
            status = future.day.condition.text,
            highTemp = future.day.maxtempC,
            lowTemp = future.day.mintempC
        )
        futureData.add(data)
    }
    return WeatherData(
        weatherStatus = currentForecast.condition.text,
        weatherStatusCode = currentForecast.condition.code,
        dateAndTime = location.localtime,
        currentTemperature = currentForecast.tempC,
        region = "${location.name}/${location.country}",
        rainPct = fullForecast.forecastDay.firstOrNull()?.day?.dailyChanceOfRain ?: 0,
        windSpeed = currentForecast.windKph,
        humidityPct = currentForecast.humidity,
        hourlyWeatherModel = hourlyData,
        futureWeatherModel = futureData
    )
}