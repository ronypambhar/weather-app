package com.app.data.repository

import com.app.data.mappers.toWeatherData
import com.app.data.network.IOpenWeatherApi
import com.app.domain.models.CityWeatherData
import com.app.domain.repositories.IOpenWeatherRepository
import java.io.IOException
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val iOpenWeatherApi: IOpenWeatherApi,
    private val openWeatherKey: String
) : IOpenWeatherRepository {

    override suspend fun getWeatherByCity(
        cityName: String,
        openWeatherUnit: String
    ): CityWeatherData {
        try {
            return iOpenWeatherApi.getWeatherByCity(
                cityName = cityName,
                openWeatherKey = openWeatherKey,
                openWeatherUnit = openWeatherUnit
            ).toWeatherData()
        } catch (exception: retrofit2.HttpException) {
            return when (exception.code()) {
                401 -> CityWeatherData.InvalidAccess
                404 -> CityWeatherData.NoCityFound
                429 -> CityWeatherData.ExceededFreeLimit
                500, 502, 503, 504 -> CityWeatherData.ContactOpenWeatherService
                else -> CityWeatherData.UnableToReachServer
            }
        } catch (exception: Exception) {
            return CityWeatherData.UnableToReachServer
        }
    }
}