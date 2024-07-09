package com.app.weatherapp.presentation.screens.citylist

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.data.utils.ConnectivityObserver
import com.app.domain.usecases.GetCitiesUseCase
import com.app.weatherapp.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    val connectionState: ConnectivityObserver,
    private val citiesUseCase: GetCitiesUseCase
) : ViewModel() {

    private val _data = MutableStateFlow(Data())
    val data = _data.asStateFlow()

    private fun getCities() {
        _data.update {
            it.copy(uiState = UiState.LOADING)
        }
        viewModelScope.launch {
            val cities = citiesUseCase.invoke()
            _data.update {
                it.copy(
                    cities = cities,
                    uiState = if (cities.isEmpty()) UiState.ERROR else UiState.SUCCESS
                )
            }
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.GetCities -> getCities()
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
        data object GetCities : Event()
        data class ConnectionState(val connectionState: ConnectivityObserver.Status) : Event()
    }

    @Immutable
    data class Data(
        val cities: List<String> = listOf(),
        val uiState: UiState = UiState.LOADING
    )
}
