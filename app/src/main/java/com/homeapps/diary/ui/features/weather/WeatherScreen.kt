package com.homeapps.diary.ui.features.weather

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.homeapps.diary.ui.features.weather.components.Forecast
import com.homeapps.diary.ui.features.weather.components.Waiting
import com.homeapps.diary.ui.features.weather.components.WeatherError
import com.homeapps.diary.utils.DiarySnackBarManager
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = koinViewModel(),
    snackBarManager: DiarySnackBarManager,
    goHome: () -> Unit = {}
) {
    val forecastState = viewModel.forecastState.collectAsStateWithLifecycle()
    val hasLocationPermission = remember { mutableStateOf(false) }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission.value = permissions.all { it.value }
        if (hasLocationPermission.value && viewModel.ifGpsOn()) {
            viewModel.loadWeatherByLocation(Locale.current.language, snackBarManager)
        } else {
            viewModel.loadWeatherByIp(Locale.current.language)
        }
    }
    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ ->
        if (viewModel.ifGpsOn()) {
            if (viewModel.hasLocationPermissions()) {
                viewModel.loadWeatherByLocation(Locale.current.language, snackBarManager)
            } else {
                viewModel.getLocationPermissions(locationPermissionLauncher)
            }
            return@rememberLauncherForActivityResult
        }
        viewModel.loadWeatherByIp(Locale.current.language)
    }

    LaunchedEffect(Unit) {
        if (!viewModel.ifGpsOn()) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            settingsLauncher.launch(intent)
            return@LaunchedEffect
        }
        if (!viewModel.hasLocationPermissions()) {
            viewModel.getLocationPermissions(locationPermissionLauncher)
            return@LaunchedEffect
        }
        if (viewModel.ifGpsOn() && viewModel.hasLocationPermissions()) {
            viewModel.loadWeatherByLocation(Locale.current.language, snackBarManager)
        } else {
            viewModel.loadWeatherByIp(Locale.current.language)
        }
    }

    AnimatedContent(
        targetState = forecastState.value,
        transitionSpec = { fadeIn().togetherWith(fadeOut()) },
    ) {
        when (it) {
            is WeatherViewModel.ForecastResult.Loading -> Waiting()

            is WeatherViewModel.ForecastResult.Success -> Forecast(weatherData = it.data)

            is WeatherViewModel.ForecastResult.Failure -> {
                WeatherError(
                    errorMessage = it.message,
                    tryAgain = { viewModel.loadWeatherByIp(Locale.current.language) },
                    goHome = goHome
                )
            }
        }
    }
}