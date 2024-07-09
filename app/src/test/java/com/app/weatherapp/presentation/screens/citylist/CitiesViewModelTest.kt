package com.app.weatherapp.presentation.screens.citylist

import app.cash.turbine.test
import com.app.data.utils.ConnectivityObserver
import com.app.domain.usecases.GetCitiesUseCase
import com.app.weatherapp.presentation.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CitiesViewModelTest {
    @Mock
    private lateinit var citiesUseCase: GetCitiesUseCase
    @Mock
    private lateinit var connectivityObserver: ConnectivityObserver

    private val testDispatchers = StandardTestDispatcher()
    private lateinit var viewModel: CitiesViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatchers)
        viewModel = CitiesViewModel(connectivityObserver, citiesUseCase)
    }

    @Test
    fun verify_InitialData() {
        val data = viewModel.data.value
        assert(data.cities.isEmpty())
        assert(data.uiState == UiState.LOADING)
    }

    @Test
    fun verify_SuccessData_On_GetCities() {
        runTest {

            val expectedResult = listOf("City1", "City2", "City3")
            val event = CitiesViewModel.Event.GetCities
            Mockito.`when`(citiesUseCase.invoke())
                .thenReturn(expectedResult)
            viewModel.onEvent(event)

            viewModel.data.test {
                var emittedItem = awaitItem()
                Assert.assertEquals(UiState.LOADING, emittedItem.uiState)

                emittedItem = awaitItem()
                Assert.assertEquals(UiState.SUCCESS, emittedItem.uiState)
                Assert.assertEquals(expectedResult, emittedItem.cities)
            }
        }
    }

    @Test
    fun verify_ErrorData_On_GetCities_With_EmptyList() {
        runTest {

            val expectedResult = listOf<String>()
            val event = CitiesViewModel.Event.GetCities
            Mockito.`when`(citiesUseCase.invoke())
                .thenReturn(expectedResult)
            viewModel.onEvent(event)

            viewModel.data.test {
                var emittedItem = awaitItem()
                Assert.assertEquals(UiState.LOADING, emittedItem.uiState)

                emittedItem = awaitItem()
                Assert.assertEquals(UiState.ERROR, emittedItem.uiState)
                Assert.assertEquals(expectedResult, emittedItem.cities)
            }
        }
    }


    @Test
    fun verify_Event_On_No_InternetConnection() {
        runTest {

            val event = CitiesViewModel.Event.ConnectionState(ConnectivityObserver.Status.Unavailable)
            viewModel.onEvent(event)

            viewModel.data.test {
                val emittedItem = awaitItem()
                Assert.assertEquals(UiState.NO_INTERNET, emittedItem.uiState)
            }
        }
    }
}
