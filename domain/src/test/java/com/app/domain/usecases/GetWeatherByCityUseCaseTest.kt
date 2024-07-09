package com.app.domain.usecases

import com.app.domain.models.CityWeatherData
import com.app.domain.repositories.IOpenWeatherRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetWeatherByCityUseCaseTest {
    @Mock
    private lateinit var iOpenWeatherRepository: IOpenWeatherRepository

    private lateinit var useCaseToTest: GetWeatherByCityUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCaseToTest = GetWeatherByCityUseCase(openWeatherRepository = iOpenWeatherRepository)
    }

    @Test
    fun verifySuccessScenarioWhenInvoked() {
        runTest {
            val cityName = "stockholm"
            val unit = "metric"
            val mockData = CityWeatherData.WeatherData()
            Mockito.`when`(iOpenWeatherRepository.getWeatherByCity(cityName, unit))
                .thenReturn(mockData)
            val result = useCaseToTest.invoke(cityName, unit)

            assertEquals(result, mockData)
        }
    }
}