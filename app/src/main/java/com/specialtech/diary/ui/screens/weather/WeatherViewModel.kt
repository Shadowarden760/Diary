package com.specialtech.diary.ui.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specialtech.diary.data.datasources.weather.models.FutureWeatherModel
import com.specialtech.diary.data.datasources.weather.models.HourlyWeatherModel
import com.specialtech.diary.data.repositories.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository): ViewModel() {
    private val _hourWeather: MutableStateFlow<List<HourlyWeatherModel>> = MutableStateFlow(emptyList())
    val hourlyWeather: StateFlow<List<HourlyWeatherModel>> = _hourWeather

    private val _futureWeather: MutableStateFlow<List<FutureWeatherModel>> = MutableStateFlow(emptyList())
    val futureWeather: StateFlow<List<FutureWeatherModel>> = _futureWeather

    fun loadWeather() = viewModelScope.launch {
        weatherRepository.getHourlyWeather().collect {
            _hourWeather.value = it
        }
        weatherRepository.getFutureWeather().collect {
            _futureWeather.value = it
        }
    }
}