package com.example.weatherapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.CityApi
import com.example.weatherapp.data.WeatherApi
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.models.WeatherData
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Composable
fun WeatherScreen() {
    var weatherState by remember { mutableStateOf<WeatherState>(WeatherState.Loading) }

    val cityApi = remember {
        Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(CityApi::class.java)
    }

    val weatherApi = remember {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    val repository = remember { WeatherRepository(cityApi, weatherApi) }

    LaunchedEffect(Unit) {
        weatherState = try {
            val data = repository.getCitiesWithWeather()
            WeatherState.Success(data)
        } catch (e: Exception) {
            WeatherState.Error(e.message ?: "Unknown error")
        }
    }

    when (val state = weatherState) {
        is WeatherState.Loading -> LoadingView()
        is WeatherState.Success -> WeatherListView(state.data)
        is WeatherState.Error -> ErrorView(message = state.message)
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorView(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
    }
}

@Composable
private fun WeatherListView(data: List<Pair<String, WeatherData>>) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(data) {
            (cityName, weather) ->
            CityWeatherCard(cityName = cityName, weather = weather)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val data: List<Pair<String, WeatherData>>) : WeatherState()
    data class Error(val message: String) : WeatherState()
}