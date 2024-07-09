package com.app.data.mappers

import com.app.data.network.entities.WeatherResponse
import com.app.data.utils.getFormatTime
import com.app.domain.models.CityWeatherData


fun WeatherResponse.toWeatherData(): CityWeatherData {
    return CityWeatherData.WeatherData(
        humidity = main?.humidity ?: 0,
        pressure = main?.pressure ?: 0,
        temp = main?.temp ?: 0.0,
        tempMax = main?.temp_max ?: 0.0,
        tempMin = main?.temp_min ?: 0.0,
        name = name.orEmpty(),
        sunriseTime = sys?.sunrise?.let { getFormatTime(it.toLong(), timezone ?: 0) } ?: "",
        sunsetTime = sys?.sunset?.let { getFormatTime(it.toLong(), timezone ?: 0) } ?: "",
        visibility = visibility?.div(1000) ?: 0,
        icon = weather?.firstOrNull()?.icon ?: "",
        main = weather?.firstOrNull()?.main ?: "",
        windSpeed = wind?.speed ?: 0.0
    )
}