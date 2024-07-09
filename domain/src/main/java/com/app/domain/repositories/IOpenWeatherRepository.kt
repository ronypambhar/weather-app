package com.app.domain.repositories

import com.app.domain.models.CityWeatherData

interface IOpenWeatherRepository {

    suspend fun getWeatherByCity(cityName: String, openWeatherUnit: String): CityWeatherData
}