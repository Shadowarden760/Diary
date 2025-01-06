package com.specialtech.diary.ui.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specialtech.diary.data.datasources.weather.models.WeatherData
import com.specialtech.diary.data.repositories.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository): ViewModel() {
    private val _forecast: MutableStateFlow<WeatherData> = MutableStateFlow(WeatherData(emptyList(), emptyList()))
    val forecast: StateFlow<WeatherData> = _forecast

    fun loadWeather() = viewModelScope.launch {
        weatherRepository.getIpAddress()?.let {
            _forecast.value = weatherRepository.getForecast(it)
        }
    }
}