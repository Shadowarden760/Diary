package com.homeapps.diary.data.datasources.weather.models.dto

import com.homeapps.diary.data.datasources.weather.models.FutureWeatherModel
import com.homeapps.diary.data.datasources.weather.models.HourlyWeatherModel
import com.homeapps.diary.data.datasources.weather.models.WeatherData
import com.homeapps.diary.data.datasources.weather.models.dto.entities.Current
import com.homeapps.diary.data.datasources.weather.models.dto.entities.Forecast
import com.homeapps.diary.data.datasources.weather.models.dto.entities.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    @SerialName("current") val currentForecast: Current,
    @SerialName("forecast") val fullForecast: Forecast,
    @SerialName("location") val location: Location
) {

    fun toWeatherData(): WeatherData {
        val hourlyData: MutableList<HourlyWeatherModel> = mutableListOf()
        val futureData: MutableList<FutureWeatherModel> = mutableListOf()
        fullForecast.forecastday.firstOrNull()?.let {
            it.hour.forEach { hourData ->
                val data = HourlyWeatherModel(
                    hour = hourData.time,
                    temperature = hourData.temp_c,
                    pictureCode = hourData.condition.code
                )
                hourlyData.add(data)
            }
        }
        fullForecast.forecastday.forEach { future ->
            val data = FutureWeatherModel(
                date = future.date,
                pictureCode = future.day.condition.code,
                status = future.day.condition.text,
                highTemp = future.day.maxtemp_c,
                lowTemp = future.day.mintemp_c
            )
            futureData.add(data)
        }
        return WeatherData(
            weatherStatus = currentForecast.condition.text,
            weatherStatusCode = currentForecast.condition.code,
            dateAndTime = location.localtime,
            currentTemperature = currentForecast.temp_c,
            region = "${location.name}/${location.country}",
            rainPct = fullForecast.forecastday.firstOrNull()?.day?.daily_chance_of_rain ?: 0,
            windSpeed = currentForecast.wind_kph,
            humidityPct = currentForecast.humidity,
            hourlyWeatherModel = hourlyData,
            futureWeatherModel = futureData
        )
    }

}