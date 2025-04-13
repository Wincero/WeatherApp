package com.example.weatherapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.weatherapp.models.WeatherData

@Composable
fun CityWeatherCard(cityName: String, weather: WeatherData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = cityName,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${weather.temperature}°C",
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
                WeatherIcon(
                    weather.weatherCode
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            WeatherDetails(
                windSpeed = weather.windSpeed,
                precipitation = weather.precipitation,
                humidity = weather.humidity
            )
        }
    }
}

@Composable
private fun WeatherDetails(
    windSpeed: Double,
    precipitation: Double,
    humidity: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        WeatherDetailItem(
            icon = Icons.Default.Air,
            value = "${windSpeed} м/с"
        )
        WeatherDetailItem(
            icon = Icons.Default.WaterDrop,
            value = "${precipitation} мм"
        )
        WeatherDetailItem(
            icon = Icons.Default.DeviceThermostat,
            value = "$humidity%"
        )
    }
}

@Composable
private fun WeatherDetailItem(icon: ImageVector, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(
            modifier = Modifier.width(4.dp)
        )
        Text(
            text = value
        )
    }
}

@Composable
private fun WeatherIcon(code: Int) {
    val icon = when(code) {
        in 0..1 -> Icons.Default.WbSunny
        in 2..3 -> Icons.Default.WbCloudy
        in 45..48 -> Icons.Default.Cloud
        in 51..67 -> Icons.Default.Grain
        in 71..77 -> Icons.Default.AcUnit
        in 80..82 -> Icons.Default.Thunderstorm
        in 95..99 -> Icons.Default.FlashOn
        else -> Icons.Default.QuestionMark
    }
    Icon(
        imageVector = icon,
        contentDescription = "Weather icon",
        modifier = Modifier.size(32.dp)
    )
}