package com.homeapps.diary.ui.features.weather

import android.content.Context
import android.location.Location
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.homeapps.diary.R
import com.homeapps.diary.data.datasources.weather.models.WeatherData
import com.homeapps.diary.data.repositories.WeatherRepository
import com.homeapps.diary.utils.DiaryLocationManager
import com.homeapps.diary.utils.DiarySnackBarManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    fun loadWeatherByLocation(userLocale: String, snackBarManager: DiarySnackBarManager
    ) = CoroutineScope(Dispatchers.IO).launch {
        _forecast.value = ForecastResult.Loading
        diaryLocationManager.requestSingleLocationUpdate(
            onLocationReceived = { location ->
                onLocationReceived(
                    location = location,
                    userLocale = userLocale
                )
            },
            onError = { locationError ->
                when (locationError) {
                    DiaryLocationManager.LocationErrors.ERROR_NO_AVAILABLE_PROVIDERS -> {
                        snackBarManager.showSnackBar(
                            message = appContext.getString(R.string.weather_text_no_available_providers),
                            actionLabel = null,
                            action = {}
                        )
                    }
                    DiaryLocationManager.LocationErrors.ERROR_REQUESTING_LOCATION -> {
                        snackBarManager.showSnackBar(
                            message = appContext.getString(R.string.weather_text_cant_get_GPS),
                            actionLabel = null,
                            action = {}
                        )
                    }
                }
                loadWeatherByIp(userLocale = userLocale)
            }
        )
    }

    fun loadWeatherByIp(userLocale: String) = CoroutineScope(Dispatchers.IO).launch {
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

    private fun onLocationReceived(location: Location, userLocale: String) = CoroutineScope(Dispatchers.IO).launch {
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
    }

    sealed class ForecastResult {
        data object Loading: ForecastResult()
        data class Success(val data: WeatherData): ForecastResult()
        data class Failure(val message: String): ForecastResult()
    }
}