package com.app.domain.usecases

import com.app.domain.repositories.ICitiesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetCitiesUseCaseTest {

    @Mock
    private lateinit var iCitiesRepository: ICitiesRepository

    private lateinit var useCaseToTest: GetCitiesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCaseToTest = GetCitiesUseCase(iCitiesRepository = iCitiesRepository)
    }

    @Test
    fun verifySuccessScenarioWhenInvoked() {
        runTest {

            val mockData = listOf("City1", "City2", "City3")
            Mockito.`when`(iCitiesRepository.getCities())
                .thenReturn(mockData)
            val result = useCaseToTest.invoke()

            Assert.assertEquals(result, mockData)
        }
    }

}