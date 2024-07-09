package com.app.weatherapp.presentation.screens.citylist

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.data.utils.ConnectivityObserver

const val CITY_LIST = "cityList"

fun NavGraphBuilder.citiesNavigation(
    navigateToCityDetail: (cityName: String) -> Unit
) {
    composable(
        route = CITY_LIST
    ) {
        val viewModel = hiltViewModel<CitiesViewModel>()
        val data = viewModel.data.collectAsStateWithLifecycle()
        val connectionState by viewModel.connectionState
            .observe()
            .collectAsState(
                initial = ConnectivityObserver.Status.Available
            )
        LaunchedEffect(null) { viewModel.onEvent(CitiesViewModel.Event.GetCities) }
        LaunchedEffect(key1 = connectionState) {
            viewModel.onEvent(CitiesViewModel.Event.ConnectionState(connectionState))
        }
        CitiesScreen(
            data = data.value,
            navigateToCityDetail = navigateToCityDetail
        )
    }
}
