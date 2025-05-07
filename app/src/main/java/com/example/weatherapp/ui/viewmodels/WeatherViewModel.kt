package com.example.weatherapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.CityApi
import com.example.weatherapp.data.WeatherApi
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.models.WeatherData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class WeatherViewModel : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    private val cityApi = Retrofit.Builder()
        .baseUrl("https://api.api-ninjas.com/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(CityApi::class.java)

    private val weatherApi = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    private val repository = WeatherRepository(cityApi, weatherApi)

    init {
        loadWeatherData()
    }

    fun loadWeatherData() {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            try {
                val data = repository.getCitiesWithWeather()
                _weatherState.value = WeatherState.Success(data)
            } catch (e: Exception) {
                _weatherState.value = WeatherState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val data: List<Pair<String, WeatherData>>) : WeatherState()
    data class Error(val message: String) : WeatherState()
}