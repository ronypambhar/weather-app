package com.app.domain.models

sealed class CityWeatherData {
    data class WeatherData(
        val humidity: Int = 0,
        val pressure: Int = 0,
        val temp: Double = 0.0,
        val tempMax: Double = 0.0,
        val tempMin: Double = 0.0,
        val name: String = "",
        val sunriseTime: String = "",
        val sunsetTime: String = "",
        val visibility: Int = 0,
        val icon: String = "",
        val main: String = "",
        val windSpeed: Double = 0.0,
    ) : CityWeatherData() {
        val iconUrl = "https://openweathermap.org/img/wn/$icon@2x.png"
    }

    data object NoCityFound: CityWeatherData()
    data object ExceededFreeLimit : CityWeatherData()
    data object InvalidAccess : CityWeatherData()
    data object ContactOpenWeatherService : CityWeatherData()
    data object UnableToReachServer : CityWeatherData()
}