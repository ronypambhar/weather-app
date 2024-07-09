package com.app.data.network

import com.app.data.network.entities.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IOpenWeatherApi {

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("appId") openWeatherKey: String,
        @Query("units") openWeatherUnit: String
    ) : WeatherResponse

}