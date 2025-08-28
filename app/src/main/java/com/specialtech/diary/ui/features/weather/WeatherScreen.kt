package com.specialtech.diary.ui.features.weather

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.specialtech.diary.ui.features.weather.components.Forecast
import com.specialtech.diary.ui.features.weather.components.Waiting
import com.specialtech.diary.ui.features.weather.components.WeatherError
import com.specialtech.diary.utils.DiarySnackBarManager
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = koinViewModel(),
    snackBarManager: DiarySnackBarManager,
    goHome: () -> Unit = {}
) {
    val forecast = viewModel.forecast.collectAsStateWithLifecycle()
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
            if (viewModel.checkLocationPermissions()) {
                viewModel.loadWeatherByLocation(Locale.current.language, snackBarManager)
            } else {
                viewModel.getLocationPermissions(locationPermissionLauncher)
            }
            return@rememberLauncherForActivityResult
        }
        viewModel.loadWeatherByIp(Locale.current.language)
    }

    LaunchedEffect(key1 = Unit) {
        if (!viewModel.ifGpsOn()) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            settingsLauncher.launch(intent)
            return@LaunchedEffect
        }
        if (!viewModel.checkLocationPermissions()) {
            viewModel.getLocationPermissions(locationPermissionLauncher)
            return@LaunchedEffect
        }
        if (viewModel.ifGpsOn() && viewModel.checkLocationPermissions()) {
            viewModel.loadWeatherByLocation(Locale.current.language, snackBarManager)
        } else {
            viewModel.loadWeatherByIp(Locale.current.language)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Waiting(
            isVisible = forecast.value is WeatherViewModel.ForecastResult.Loading
        )
        WeatherError(
            isVisible = forecast.value is WeatherViewModel.ForecastResult.Failure,
            errorMessage = if (forecast.value is WeatherViewModel.ForecastResult.Failure) (forecast.value as WeatherViewModel.ForecastResult.Failure).message else "",
            tryAgain = { viewModel.loadWeatherByIp(Locale.current.language) },
            goHome = goHome
        )
        Forecast(
            isVisible = forecast.value is WeatherViewModel.ForecastResult.Success,
            forecastResult = forecast.value
        )
    }
}
