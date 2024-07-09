package com.app.weatherapp.presentation.screens.citydetail

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.data.utils.ConnectivityObserver
import com.app.domain.models.CityWeatherData
import com.app.domain.usecases.GetWeatherByCityUseCase
import com.app.weatherapp.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    val connectionState: ConnectivityObserver,
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase
) : ViewModel() {

    private val _data = MutableStateFlow(Data())
    val data = _data.asStateFlow()


    private fun getWeatherByCity(cityName: String, openWeatherUnit: String) {
        viewModelScope.launch {
            _data.update { it.copy(uiState = UiState.LOADING) }

            when (val weatherData = getWeatherByCityUseCase.invoke(cityName, openWeatherUnit)) {
                is CityWeatherData.WeatherData -> _data.update {
                    it.copy(
                        weatherData = weatherData,
                        errorData = null,
                        uiState = UiState.SUCCESS
                    )
                }

                else -> _data.update {
                    it.copy(
                        errorData = weatherData,
                        uiState = UiState.ERROR
                    )
                }
            }
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.GetWeather -> getWeatherByCity(event.cityName, event.openWeatherUnit)
            is Event.ConnectionState -> {
                val uiState = when(event.connectionState) {
                    ConnectivityObserver.Status.Available -> UiState.SUCCESS
                    else -> UiState.NO_INTERNET
                }
                _data.update {
                    it.copy(
                        uiState = uiState
                    )
                }
            }
        }
    }

    sealed class Event {
        data class GetWeather(val cityName: String, val openWeatherUnit: String) : Event()
        data class ConnectionState(val connectionState: ConnectivityObserver.Status) : Event()
    }

    @Immutable
    data class Data(
        val weatherData: CityWeatherData.WeatherData = CityWeatherData.WeatherData(),
        val errorData: CityWeatherData? = null,
        val uiState: UiState = UiState.LOADING
    )
}
