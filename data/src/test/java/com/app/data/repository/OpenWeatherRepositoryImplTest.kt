package com.app.data.repository

import com.app.data.network.IOpenWeatherApi
import com.app.data.network.entities.WeatherResponse
import com.app.domain.models.CityWeatherData
import com.app.domain.repositories.IOpenWeatherRepository
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

class OpenWeatherRepositoryImplTest {

    @Mock
    private lateinit var iOpenWeatherApi: IOpenWeatherApi
    private val openWeatherKey = "abcd1234"

    private lateinit var repositoryToTest: IOpenWeatherRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repositoryToTest = OpenWeatherRepositoryImpl(
            iOpenWeatherApi = iOpenWeatherApi,
            openWeatherKey = openWeatherKey
        )
    }

    @Test
    fun verifySuccessScenarioWhenGetWeatherByCityTriggers() {
        runTest {
            val cityName = "Stockholm"
            val openWeatherUnit = "metric"

            val mockResponse = getMockWeatherResponse(cityName)
            Mockito.`when`(
                iOpenWeatherApi.getWeatherByCity(cityName, openWeatherKey, openWeatherUnit)
            ).then { mockResponse }

            val result = repositoryToTest.getWeatherByCity(cityName, openWeatherUnit)

            assert(result is CityWeatherData.WeatherData)
            Assert.assertEquals((result as CityWeatherData.WeatherData).name, cityName)
        }
    }

    @Test
    fun verifyInvalidAccessScenarioWhenGetWeatherByCityTriggers() {
        runTest {
            val cityName = "Stockholm"
            val openWeatherUnit = "metric"

            val mockErrorResponse = getHttpErrorResponse(401)
            Mockito.`when`(
                iOpenWeatherApi.getWeatherByCity(cityName, openWeatherKey, openWeatherUnit)
            ).thenThrow(HttpException(mockErrorResponse))


            val result = repositoryToTest.getWeatherByCity(cityName, openWeatherUnit)

            assert(result is CityWeatherData.InvalidAccess)
        }
    }

    @Test
    fun verifyNoCityFoundScenarioWhenGetWeatherByCityTriggers() {
        runTest {
            val cityName = "Stockholm"
            val openWeatherUnit = "metric"

            val mockErrorResponse = getHttpErrorResponse(404)
            Mockito.`when`(
                iOpenWeatherApi.getWeatherByCity(cityName, openWeatherKey, openWeatherUnit)
            ).thenThrow(HttpException(mockErrorResponse))


            val result = repositoryToTest.getWeatherByCity(cityName, openWeatherUnit)

            assert(result is CityWeatherData.NoCityFound)
        }
    }

    @Test
    fun verifyExceededFreeLimitScenarioWhenGetWeatherByCityTriggers() {
        runTest {
            val cityName = "Stockholm"
            val openWeatherUnit = "metric"

            val mockErrorResponse = getHttpErrorResponse(429)
            Mockito.`when`(
                iOpenWeatherApi.getWeatherByCity(cityName, openWeatherKey, openWeatherUnit)
            ).thenThrow(HttpException(mockErrorResponse))


            val result = repositoryToTest.getWeatherByCity(cityName, openWeatherUnit)

            assert(result is CityWeatherData.ExceededFreeLimit)
        }
    }

    @Test
    fun verifyContactOpenWeatherServiceScenarioWhenGetWeatherByCityTriggers() {
        runTest {
            val cityName = "Stockholm"
            val openWeatherUnit = "metric"

            val mockErrorResponse = getHttpErrorResponse(500)
            Mockito.`when`(
                iOpenWeatherApi.getWeatherByCity(cityName, openWeatherKey, openWeatherUnit)
            ).thenThrow(HttpException(mockErrorResponse))


            val result = repositoryToTest.getWeatherByCity(cityName, openWeatherUnit)

            assert(result is CityWeatherData.ContactOpenWeatherService)
        }
    }

    @Test
    fun verifyUnableToReachServerScenarioWhenGetWeatherByCityTriggers() {
        runTest {
            val cityName = "Stockholm"
            val openWeatherUnit = "metric"

            Mockito.`when`(
                iOpenWeatherApi.getWeatherByCity(cityName, openWeatherKey, openWeatherUnit)
            ).thenAnswer {  throw Exception() }


            val result = repositoryToTest.getWeatherByCity(cityName, openWeatherUnit)

            assert(result is CityWeatherData.UnableToReachServer)
        }
    }



    private fun getMockWeatherResponse(cityName: String): WeatherResponse {
        return WeatherResponse(
            base = null,
            clouds = null,
            cod = null,
            coord = null,
            dt = null,
            id = null,
            main = null,
            name = cityName,
            rain = null,
            sys = null,
            timezone = null,
            visibility = null,
            weather = null,
            wind = null
        )
    }

    private fun getHttpErrorResponse(code: Int): Response<HttpException> {
        return Response.error(
            code,
            "Something went wrong".toResponseBody()
        )
    }

}