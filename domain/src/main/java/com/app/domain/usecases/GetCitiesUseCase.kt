package com.app.domain.usecases

import com.app.domain.repositories.ICitiesRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val iCitiesRepository: ICitiesRepository
) {
    suspend operator fun invoke(): List<String> {
        return iCitiesRepository.getCities()
    }
}