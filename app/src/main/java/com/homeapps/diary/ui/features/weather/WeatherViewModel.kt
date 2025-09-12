package com.homeapps.diary.ui.features.weather

import android.content.Context
import android.location.Location
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.R
import com.homeapps.diary.domain.models.weather.WeatherData
import com.homeapps.diary.domain.usecases.weather.GetForecastUseCase
import com.homeapps.diary.domain.usecases.weather.GetIpAddressUseCase
import com.homeapps.diary.utils.DiaryLocationManager
import com.homeapps.diary.utils.DiarySnackBarManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val appContext: Context,
    private val getIpAddressUseCase: GetIpAddressUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
): ViewModel() {
    private val diaryLocationManager = DiaryLocationManager(appContext)
    private val _forecastState: MutableStateFlow<ForecastState> = MutableStateFlow(ForecastState.Loading)
    val forecastState: StateFlow<ForecastState> = _forecastState

    fun ifGpsOn() = diaryLocationManager.ifGpsOn()

    fun hasLocationPermissions() = diaryLocationManager.hasLocationPermissions()

    fun getLocationPermissions(launcher: ActivityResultLauncher<Array<String>>) {
        launcher.launch(diaryLocationManager.locationPermissions)
    }

    fun loadWeatherByLocation(
        userLocale: String,
        snackBarManager: DiarySnackBarManager
    ) = viewModelScope.launch {
        _forecastState.value = ForecastState.Loading
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
                    DiaryLocationManager.LocationErrors.ERROR_LOCATION_TIMEOUT -> {
                        snackBarManager.showSnackBar(
                            message = appContext.getString(R.string.weather_text_GPS_timeout),
                            actionLabel = null,
                            action = {}
                        )
                    }
                }
                loadWeatherByIp(userLocale = userLocale)
            }
        )
    }

    fun loadWeatherByIp(userLocale: String) = viewModelScope.launch {
        _forecastState.value = ForecastState.Loading
        val ipResponse = withContext(dispatcher) {
            getIpAddressUseCase()
        }
        if (ipResponse.ip != null) {
            val forecastResult = withContext(dispatcher) {
                getForecastUseCase(qParams = ipResponse.ip, locale = userLocale)
            }
            when (forecastResult) {
                is WeatherData -> _forecastState.value = ForecastState.Success(data = forecastResult)
                null -> _forecastState.value = ForecastState.Failure(
                    message = appContext.getString(R.string.weather_text_cant_get_weather_data)
                )
            }
        } else {
            if (ipResponse.errorMessage.isNotEmpty()) {
                _forecastState.value = ForecastState.Failure(message = ipResponse.errorMessage)
            } else {
                _forecastState.value = ForecastState.Failure(message = appContext.getString(R.string.weather_text_cant_get_ip_address))
            }
        }
    }

    private fun onLocationReceived(location: Location, userLocale: String) = viewModelScope.launch {
        val forecastResult = withContext(dispatcher) {
            getForecastUseCase(
                qParams = "${location.latitude},${location.longitude}",
                locale = userLocale
            )
        }
        when (forecastResult) {
            is WeatherData -> {
                _forecastState.value = ForecastState.Success(
                    data = forecastResult,
                    userLocation = location
                )
            }
            null -> _forecastState.value = ForecastState.Failure(
                message = appContext.getString(R.string.weather_text_cant_get_weather_data)
            )
        }
    }

    sealed class ForecastState {
        data object Loading: ForecastState()
        data class Success(val data: WeatherData, val userLocation: Location? = null): ForecastState()
        data class Failure(val message: String): ForecastState()
    }
}