package com.homeapps.diary.domain.usecases.weather

import com.homeapps.diary.domain.api.WeatherRepository
import com.homeapps.diary.domain.models.weather.IpAddressData

class GetIpAddressUseCase(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(): IpAddressData {
        return weatherRepository.getIpAddress()
    }
}