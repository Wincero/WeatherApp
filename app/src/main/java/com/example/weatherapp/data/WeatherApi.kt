package com.example.weatherapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,weather_code,precipitation,wind_speed_10m,relative_humidity_2m"
    ): WeatherResponse
}

data class WeatherResponse(
    val current: CurrentWeather
)

data class CurrentWeather(
    val time: String,
    val temperature_2m: Double,
    val weather_code: Int,
    val precipitation: Double,
    val wind_speed_10m: Double,
    val relative_humidity_2m: Int
)