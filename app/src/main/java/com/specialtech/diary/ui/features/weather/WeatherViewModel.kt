package com.specialtech.diary.ui.features.weather

import android.Manifest
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specialtech.diary.R
import com.specialtech.diary.data.datasources.weather.models.WeatherData
import com.specialtech.diary.data.repositories.WeatherRepository
import com.specialtech.diary.utils.DiaryLocationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val appContext: Context
): ViewModel() {
    private val diaryLocationManager = DiaryLocationManager(appContext)
    private val _forecast: MutableStateFlow<ForecastResult> = MutableStateFlow(ForecastResult.Loading)
    val forecast: StateFlow<ForecastResult> = _forecast

    fun ifGpsOn() = diaryLocationManager.ifGpsOn()

    fun checkLocationPermissions() = diaryLocationManager.hasLocationPermissions()

    fun getLocationPermissions(launcher: ActivityResultLauncher<Array<String>>) {
        launcher.launch(diaryLocationManager.locationPermissions)
    }

    fun loadWeatherByIp(userLocale: String) = viewModelScope.launch {
        _forecast.value = ForecastResult.Loading
        val ipResponse = weatherRepository.getIpAddress()
        if (ipResponse.ip != null) {
            val forecastResult = weatherRepository.getForecast(
                qParam = ipResponse.ip,
                userLocale = userLocale
            )
            when (forecastResult) {
                is WeatherData -> _forecast.value = ForecastResult.Success(forecastResult)
                null -> _forecast.value = ForecastResult.Failure(
                    message = appContext.getString(R.string.weather_text_cant_get_weather_data)
                )
            }
        } else {
            if (ipResponse.errorMessage.isNotEmpty()) {
                _forecast.value = ForecastResult.Failure(message = ipResponse.errorMessage)
            } else {
                _forecast.value = ForecastResult.Failure(message = appContext.getString(R.string.weather_text_cant_get_ip_address))
            }
        }
    }

    fun loadWeatherByLocation(userLocale: String) = viewModelScope.launch {
        _forecast.value = ForecastResult.Loading
        val location = diaryLocationManager.getLastKnownLocation()
        if (location != null) {
            val forecastResult = weatherRepository.getForecast(
                qParam = "${location.latitude},${location.longitude}",
                userLocale = userLocale
            )
            when (forecastResult) {
                is WeatherData -> _forecast.value = ForecastResult.Success(forecastResult)
                null -> _forecast.value = ForecastResult.Failure(
                    message = appContext.getString(R.string.weather_text_cant_get_weather_data)
                )
            }
        } else {
            loadWeatherByIp(userLocale = userLocale)
        }
    }

    sealed class ForecastResult {
        data object Loading: ForecastResult()
        data class Success(val data: WeatherData): ForecastResult()
        data class Failure(val message: String): ForecastResult()
    }
}