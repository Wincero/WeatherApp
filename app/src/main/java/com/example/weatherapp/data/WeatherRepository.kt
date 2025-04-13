package com.example.weatherapp.data

import com.example.weatherapp.models.WeatherData

class WeatherRepository(
    private val cityApi: CityApi,
    private val weatherApi: WeatherApi
) {
    private val cityNames = listOf(
        "Moscow", "Saint Petersburg", "Novosibirsk",
        "Yekaterinburg", "Kazan", "Nizhny Novgorod",
        "Chelyabinsk", "Samara", "Omsk", "Rostov-on-Don",
        "Ufa", "Krasnoyarsk", "Perm", "Voronezh", "Volgograd"
    )

    suspend fun getCitiesWithWeather(): List<Pair<String, WeatherData>> {
        return cityNames.mapNotNull { cityName ->
            try {
                val coordinates = cityApi.getCityCoordinates(cityName).firstOrNull()
                    ?: return@mapNotNull null

                val weather = weatherApi.getWeather(
                    coordinates.latitude,
                    coordinates.longitude
                )

                cityName to weather.toWeatherData()
            } catch (e: Exception) {
                null
            }
        }
    }

    private fun WeatherResponse.toWeatherData() = WeatherData(
        time = current.time,
        temperature = current.temperature_2m,
        weatherCode = current.weather_code,
        precipitation = current.precipitation,
        windSpeed = current.wind_speed_10m,
        humidity = current.relative_humidity_2m
    )
}