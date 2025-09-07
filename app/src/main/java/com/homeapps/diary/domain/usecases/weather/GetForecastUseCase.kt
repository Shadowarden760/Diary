package com.homeapps.diary.domain.usecases.weather

import com.homeapps.diary.domain.api.WeatherRepository
import com.homeapps.diary.domain.models.weather.WeatherData

class GetForecastUseCase(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(qParams: String, locale: String): WeatherData? {
        return weatherRepository.getForecast(qParam = qParams, locale = locale)
    }
}