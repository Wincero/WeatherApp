package com.example.weatherapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.models.WeatherData
import com.example.weatherapp.ui.viewmodels.WeatherState
import com.example.weatherapp.ui.viewmodels.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val weatherState by viewModel.weatherState.collectAsState()

    when (val state = weatherState) {
        is WeatherState.Loading -> LoadingView()
        is WeatherState.Success -> WeatherListView(state.data)
        is WeatherState.Error -> ErrorView(message = state.message) {
            viewModel.loadWeatherData()
        }
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
private fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Попробовать снова")
        }
    }
}

@Composable
private fun WeatherListView(data: List<Pair<String, WeatherData>>) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(data) { (cityName, weather) ->
            CityWeatherCard(cityName = cityName, weather = weather)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}