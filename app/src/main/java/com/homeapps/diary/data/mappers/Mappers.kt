package com.homeapps.diary.data.mappers

import com.homeapps.diary.AlarmDBO
import com.homeapps.diary.NoteDBO
import com.homeapps.diary.data.datasources.weather.models.dto.ForecastDTO
import com.homeapps.diary.domain.models.alarm.AlarmItem
import com.homeapps.diary.domain.models.notes.NoteData
import com.homeapps.diary.domain.models.weather.FutureWeatherData
import com.homeapps.diary.domain.models.weather.HourlyWeatherData
import com.homeapps.diary.domain.models.weather.WeatherData

fun ForecastDTO.toWeatherData(): WeatherData {
    val hourlyData: MutableList<HourlyWeatherData> = mutableListOf()
    val futureData: MutableList<FutureWeatherData> = mutableListOf()
    fullForecast.forecastDay.firstOrNull()?.let {
        it.hour.forEach { hourData ->
            val data = HourlyWeatherData(
                hour = hourData.time,
                temperature = hourData.tempC,
                pictureCode = hourData.condition.code
            )
            hourlyData.add(data)
        }
    }
    fullForecast.forecastDay.forEach { future ->
        val data = FutureWeatherData(
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
        dateAndTime = locationDTO.localtime,
        currentTemperature = currentForecast.tempC,
        region = "${locationDTO.name}/${locationDTO.country}",
        rainPct = fullForecast.forecastDay.firstOrNull()?.day?.dailyChanceOfRain ?: 0,
        windSpeed = currentForecast.windKph,
        humidityPct = currentForecast.humidity,
        hourlyWeatherData = hourlyData,
        futureWeatherData = futureData
    )
}

fun NoteDBO.toNoteData(): NoteData {
    return NoteData(
        noteId = this.noteId,
        noteTitle = this.noteTitle,
        noteMessage = this.noteMessage,
        noteCreatedAt = this.noteCreatedAt,
        noteUpdatedAt = this.noteUpdatedAt,
    )
}

fun AlarmDBO.toAlarmItem(): AlarmItem {
    return AlarmItem(
        alarmId = this.alarmId,
        alarmTimeMillis = this.alarmTimeMillis
    )
}