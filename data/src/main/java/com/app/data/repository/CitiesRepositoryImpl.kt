package com.app.data.repository

import com.app.data.local.CitiesData
import com.app.domain.repositories.ICitiesRepository
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val citiesData: CitiesData
) : ICitiesRepository {

    override suspend fun getCities(): List<String> {
       return citiesData.getCities()
    }
}