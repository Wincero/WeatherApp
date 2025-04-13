package com.example.weatherapp.data

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CityApi {
    @GET("geocoding")
    suspend fun getCityCoordinates(
        @Query("city") cityName: String,
        @Header("X-Api-Key") apiKey: String = "H4qkEcASPBKsiIhNISZOIg==7hOU88mOfTkr8j1W"
    ): List<CityCoordinatesResponse>
}

data class CityCoordinatesResponse(
    val name: String,
    val latitude: Double,
    val longitude: Double
)