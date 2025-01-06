package com.specialtech.diary.data.repositories

import com.specialtech.diary.data.datasources.weather.WeatherDataSource

class WeatherRepository(private val weatherDataSource: WeatherDataSource) {

    fun getHourlyWeather() = weatherDataSource.getHourlyWeather()

    fun getFutureWeather() = weatherDataSource.getFutureWeather()

}