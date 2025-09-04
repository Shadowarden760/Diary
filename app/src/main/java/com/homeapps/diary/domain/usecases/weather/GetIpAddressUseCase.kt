package com.homeapps.diary.domain.usecases.weather

import com.homeapps.diary.domain.api.WeatherDataApi
import com.homeapps.diary.domain.models.weather.IpAddressData

class GetIpAddressUseCase(private val weatherDataApi: WeatherDataApi) {

    suspend operator fun invoke(): IpAddressData {
        return weatherDataApi.getIpAddress()
    }
}