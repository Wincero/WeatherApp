package com.example.weatherapp.models

data class WeatherData(
    val time: String,
    val temperature: Double,
    val weatherCode: Int,
    val precipitation: Double,
    val windSpeed: Double,
    val humidity: Int
)