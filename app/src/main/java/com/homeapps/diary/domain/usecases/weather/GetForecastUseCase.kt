package com.homeapps.diary.domain.usecases.weather

import com.homeapps.diary.domain.api.WeatherDataApi
import com.homeapps.diary.domain.models.weather.WeatherData

class GetForecastUseCase(private val weatherDataApi: WeatherDataApi) {

    suspend operator fun invoke(qParams: String, locale: String): WeatherData? {
        return weatherDataApi.getForecast(qParam = qParams, locale = locale)
    }
}