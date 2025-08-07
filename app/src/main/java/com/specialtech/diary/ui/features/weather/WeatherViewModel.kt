package com.specialtech.diary.ui.features.weather

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specialtech.diary.R
import com.specialtech.diary.data.datasources.weather.models.WeatherData
import com.specialtech.diary.data.repositories.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val appContext: Context
): ViewModel() {
    private val _forecast: MutableStateFlow<ForecastResult> = MutableStateFlow(ForecastResult.Loading)
    val forecast: StateFlow<ForecastResult> = _forecast

    fun loadWeather(userLocale: String) = viewModelScope.launch {
        _forecast.value = ForecastResult.Loading
        val ipResponse = weatherRepository.getIpAddress()
        if (ipResponse.ip != null) {
            val forecastResult = weatherRepository.getForecast(
                ipAddress = ipResponse.ip,
                userLocale = userLocale
            )
            when (forecastResult) {
                is WeatherData -> _forecast.value = ForecastResult.Success(forecastResult)
                null -> _forecast.value = ForecastResult.Failure(
                    message = appContext.getString(R.string.weather_text_cant_get_weather_data)
                )
            }
        } else {
            _forecast.value = ForecastResult.Failure(message = ipResponse.errorMessage)
        }
    }

    sealed class ForecastResult {
        data object Loading: ForecastResult()
        data class Success(val data: WeatherData): ForecastResult()
        data class Failure(val message: String): ForecastResult()
    }

}