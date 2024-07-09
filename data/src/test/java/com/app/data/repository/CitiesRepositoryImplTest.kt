package com.app.data.repository

import com.app.data.local.CitiesData
import com.app.domain.repositories.ICitiesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CitiesRepositoryImplTest {
    @Mock
    private lateinit var citiesData: CitiesData

    private lateinit var repositoryToTest : ICitiesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repositoryToTest = CitiesRepositoryImpl(citiesData)
    }

    @Test
    fun verifySuccessScenarioWhenGetCitiesTriggers() {
        runTest {
            Mockito.`when`(citiesData.getCities()).thenReturn(listOf("City1","City2","City3"))
            val result = repositoryToTest.getCities()

            Mockito.verify(citiesData).getCities()
            Assert.assertTrue(result.size == 3)
        }
    }

}