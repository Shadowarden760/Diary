package com.specialtech.diary.data.datasources.weather.models.dto

import android.annotation.SuppressLint
import com.specialtech.diary.data.datasources.weather.models.FutureWeatherModel
import com.specialtech.diary.data.datasources.weather.models.HourlyWeatherModel
import com.specialtech.diary.data.datasources.weather.models.WeatherData
import com.specialtech.diary.data.datasources.weather.models.dto.entities.Current
import com.specialtech.diary.data.datasources.weather.models.dto.entities.Forecast
import com.specialtech.diary.data.datasources.weather.models.dto.entities.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    @SerialName("current") val currentForecast: Current,
    @SerialName("forecast") val fullForecast: Forecast,
    @SerialName("location") val location: Location
) {
    @SuppressLint("DefaultLocale")
    fun toWeatherData(): WeatherData {
        val hourlyData: MutableList<HourlyWeatherModel> = mutableListOf()
        val futureData: MutableList<FutureWeatherModel> = mutableListOf()
        this.fullForecast.forecastday.firstOrNull()?.let {
            it.hour.forEach { hourData ->
                val data = HourlyWeatherModel(
                    hour = hourData.time,
                    temperature = hourData.temp_c,
                    pictureCode = hourData.condition.code
                )
                hourlyData.add(data)
            }
        }
        this.fullForecast.forecastday.forEach { future ->
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
            weatherStatus = this.currentForecast.condition.text,
            weatherStatusCode = this.currentForecast.condition.code,
            dateAndTime = this.location.localtime,
            currentTemperature = this.currentForecast.temp_c,
            region = "${this.location.name}/${this.location.country}",
            rainPct = this.fullForecast.forecastday.firstOrNull()?.day?.daily_chance_of_rain ?: 0,
            windSpeed = this.currentForecast.wind_kph,
            humidityPct = this.currentForecast.humidity,
            hourlyWeatherModel = hourlyData,
            futureWeatherModel = futureData
        )
    }
}