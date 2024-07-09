package com.app.domain.usecases

import com.app.domain.models.CityWeatherData
import com.app.domain.repositories.IOpenWeatherRepository
import javax.inject.Inject

class GetWeatherByCityUseCase @Inject constructor(
    private val openWeatherRepository: IOpenWeatherRepository
) {
    suspend operator fun invoke(cityName: String, openWeatherUnit: String): CityWeatherData {
        return openWeatherRepository.getWeatherByCity(cityName, openWeatherUnit)
    }
}