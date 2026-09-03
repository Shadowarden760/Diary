package com.homeapps.diary.ui.features.weather

import android.content.Context
import android.location.Location
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.viewModelScope
import com.homeapps.diary.R
import com.homeapps.diary.domain.api.LoggingRepository
import com.homeapps.diary.domain.models.weather.WeatherData
import com.homeapps.diary.domain.usecases.logging.CreateLogMessageUseCase
import com.homeapps.diary.domain.usecases.weather.GetForecastUseCase
import com.homeapps.diary.domain.usecases.weather.GetIpAddressUseCase
import com.homeapps.diary.ui.BaseViewModel
import com.homeapps.diary.utils.DiaryLocationManager
import com.homeapps.diary.utils.DiarySnackBarManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    appContext: Context,
    private val getIpAddressUseCase: GetIpAddressUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    createLogMessageUseCase: CreateLogMessageUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
): BaseViewModel(
    appContext = appContext,
    createLogMessageUseCase = createLogMessageUseCase,
    getLogMessagesUseCase = null,
) {
    private val diaryLocationManager = DiaryLocationManager(appContext = getAppContext())
    val forecastState: StateFlow<ForecastState>
        field = MutableStateFlow<ForecastState>(ForecastState.Loading)

    fun ifGpsOn() = diaryLocationManager.ifGpsOn()

    fun hasLocationPermissions() = diaryLocationManager.hasLocationPermissions()

    fun getLocationPermissions(launcher: ActivityResultLauncher<Array<String>>) {
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${WeatherViewModel::class.java}: getting location permission"
        )
        launcher.launch(diaryLocationManager.locationPermissions)
    }

    fun loadWeatherByLocation(
        userLocale: String,
        snackBarManager: DiarySnackBarManager,
    ) = viewModelScope.launch {
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${WeatherViewModel::class.java}: getting weather by Location"
        )
        forecastState.value = ForecastState.Loading
        diaryLocationManager.requestSingleLocationUpdate(
            onLocationReceived = { location ->
                onLocationReceived(location = location, userLocale = userLocale)
            },
            onError = { locationError ->
                when (locationError) {
                    DiaryLocationManager.LocationErrors.ERROR_NO_AVAILABLE_PROVIDERS -> {
                        snackBarManager.showSnackBar(
                            message = getAppContext().getString(R.string.weather_text_no_available_providers),
                            actionLabel = null,
                            action = {}
                        )
                    }
                    DiaryLocationManager.LocationErrors.ERROR_REQUESTING_LOCATION -> {
                        snackBarManager.showSnackBar(
                            message = getAppContext().getString(R.string.weather_text_cant_get_GPS),
                            actionLabel = null,
                            action = {}
                        )
                    }
                    DiaryLocationManager.LocationErrors.ERROR_LOCATION_TIMEOUT -> {
                        snackBarManager.showSnackBar(
                            message = getAppContext().getString(R.string.weather_text_GPS_timeout),
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
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${WeatherViewModel::class.java}: getting weather by IP"
        )
        forecastState.value = ForecastState.Loading
        val ipResponse = withContext(dispatcher) {
            getIpAddressUseCase()
        }
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${WeatherViewModel::class.java}: current ip - ${ipResponse.ip}"
        )
        if (ipResponse.ip != null) {
            val forecastResult = withContext(dispatcher) {
                getForecastUseCase(qParams = ipResponse.ip, locale = userLocale)
            }
            createLogMessage(
                logLevel = LoggingRepository.LogLevel.INFO,
                logMessage = "${WeatherViewModel::class.java}: weather forecast was get - $forecastResult"
            )
            when (forecastResult) {
                is WeatherData -> forecastState.value = ForecastState.Success(data = forecastResult)
                null -> forecastState.value = ForecastState.Failure(
                    message = getAppContext().getString(R.string.weather_text_cant_get_weather_data)
                )
            }
        } else {
            if (ipResponse.errorMessage.isNotEmpty()) {
                forecastState.value = ForecastState.Failure(message = ipResponse.errorMessage)
            } else {
                forecastState.value = ForecastState.Failure(message = getAppContext().getString(R.string.weather_text_cant_get_ip_address))
            }
        }
    }

    private fun onLocationReceived(location: Location, userLocale: String) = viewModelScope.launch {
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${WeatherViewModel::class.java}: location was received (${location.latitude}, ${location.longitude})"
        )
        val forecastResult = withContext(dispatcher) {
            getForecastUseCase(
                qParams = "${location.latitude},${location.longitude}",
                locale = userLocale
            )
        }
        createLogMessage(
            logLevel = LoggingRepository.LogLevel.INFO,
            logMessage = "${WeatherViewModel::class.java}: weather forecast was get - $forecastResult"
        )
        when (forecastResult) {
            is WeatherData -> {
                forecastState.value = ForecastState.Success(
                    data = forecastResult,
                    userLocation = location
                )
            }
            null -> {
                forecastState.value = ForecastState.Failure(
                    message = getAppContext().getString(R.string.weather_text_cant_get_weather_data)
                )
            }
        }
    }

    sealed class ForecastState {
        data object Loading: ForecastState()
        data class Success(val data: WeatherData, val userLocation: Location? = null): ForecastState()
        data class Failure(val message: String): ForecastState()
    }
}