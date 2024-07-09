package com.app.weatherapp.presentation.screens.citydetail

import app.cash.turbine.test
import com.app.data.utils.ConnectivityObserver
import com.app.domain.models.CityWeatherData
import com.app.domain.usecases.GetWeatherByCityUseCase
import com.app.weatherapp.presentation.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CityViewModelTest {
    @Mock
    private lateinit var getWeatherByCityUseCase: GetWeatherByCityUseCase

    @Mock
    private lateinit var connectivityObserver: ConnectivityObserver
    private val testDispatchers = StandardTestDispatcher()
    private lateinit var viewModel: CityViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatchers)
        viewModel = CityViewModel(connectivityObserver, getWeatherByCityUseCase)
    }

    @Test
    fun verify_InitialData() {
        val data = viewModel.data.value
        assert(data.errorData == null)
        assert(data.weatherData == CityWeatherData.WeatherData())
        assert(data.uiState == UiState.LOADING)
    }

    @Test
    fun verify_SuccessData_On_GetWeatherEvent() {
        runTest {
            val cityName = "Stockholm"
            val openWeatherUnit = "metric"
            val expectedResult = CityWeatherData.WeatherData(name = cityName)
            val event = CityViewModel.Event.GetWeather(cityName, openWeatherUnit)
            Mockito.`when`(getWeatherByCityUseCase.invoke(cityName, openWeatherUnit))
                .thenReturn(expectedResult)
            viewModel.onEvent(event)

            viewModel.data.test {
                var emittedItem = awaitItem()
                assertEquals(UiState.LOADING, emittedItem.uiState)

                emittedItem = awaitItem()
                assertEquals(UiState.SUCCESS, emittedItem.uiState)
                assertEquals(expectedResult.name, emittedItem.weatherData.name)
            }
        }
    }

    @Test
    fun verify_AnyErrorData_On_GetWeatherEvent() {
        runTest {
            val cityName = "Stockholm"
            val openWeatherUnit = "metric"

            /** Here error data can be
             * [CityWeatherData.NoCityFound]
             * [CityWeatherData.ExceededFreeLimit]
             * [CityWeatherData.InvalidAccess]
             * [CityWeatherData.ContactOpenWeatherService]
             * [CityWeatherData.UnableToReachServer]
             */
            val expectedResult = CityWeatherData.InvalidAccess
            val event = CityViewModel.Event.GetWeather(cityName, openWeatherUnit)
            Mockito.`when`(getWeatherByCityUseCase.invoke(cityName, openWeatherUnit))
                .thenReturn(expectedResult)
            viewModel.onEvent(event)

            viewModel.data.test {
                var emittedItem = awaitItem()
                assertEquals(UiState.LOADING, emittedItem.uiState)

                emittedItem = awaitItem()
                assertEquals(UiState.ERROR, emittedItem.uiState)
                assertEquals(expectedResult, emittedItem.errorData)
            }
        }
    }

    @Test
    fun verify_Event_On_No_InternetConnection() {
        runTest {

            val event = CityViewModel.Event.ConnectionState(ConnectivityObserver.Status.Unavailable)
            viewModel.onEvent(event)

            viewModel.data.test {
                val emittedItem = awaitItem()
                assertEquals(UiState.NO_INTERNET, emittedItem.uiState)
            }
        }
    }
}
