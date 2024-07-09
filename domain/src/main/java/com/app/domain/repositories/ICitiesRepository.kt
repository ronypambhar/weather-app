package com.app.domain.repositories

interface ICitiesRepository {

    suspend fun getCities(): List<String>
}