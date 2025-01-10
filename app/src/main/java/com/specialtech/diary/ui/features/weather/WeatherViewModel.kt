package com.specialtech.diary.ui.features.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specialtech.diary.data.datasources.weather.models.WeatherData
import com.specialtech.diary.data.repositories.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository): ViewModel() {
    private val _forecast: MutableStateFlow<ForecastResult> = MutableStateFlow(ForecastResult.Loading)
    val forecast: StateFlow<ForecastResult> = _forecast

    fun loadWeather() = viewModelScope.launch {
        _forecast.value = ForecastResult.Loading
        val ipResponse = weatherRepository.getIpAddress()
        if (ipResponse != null) {
            val forecastResult = weatherRepository.getForecast(ipResponse)
            _forecast.value = ForecastResult.Success(forecastResult)
        } else {
            _forecast.value = ForecastResult.Failure(message = "error message")
        }
    }

    sealed class ForecastResult {
        data object Loading: ForecastResult()
        data class Success(val data: WeatherData): ForecastResult()
        data class Failure(val message: String): ForecastResult()
    }
}