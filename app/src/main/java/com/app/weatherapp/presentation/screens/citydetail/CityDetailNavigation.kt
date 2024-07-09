package com.app.weatherapp.presentation.screens.citydetail

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.data.utils.ConnectivityObserver

private const val ARG_CITY_NAME = "cityName"
const val CITY_DETAIL = "cityDetail/{$ARG_CITY_NAME}"

fun NavController.navigateToCityDetail(cityName: String) {
    navigate(
        route = CITY_DETAIL.replace("{$ARG_CITY_NAME}", cityName)
    )
}

fun NavGraphBuilder.cityDetailNavigation(onBackPressed: () -> Unit) {
    composable(
        route = CITY_DETAIL,
        arguments = listOf(navArgument(ARG_CITY_NAME) { type = NavType.StringType })
    ) {
        val cityName = it.arguments?.getString(ARG_CITY_NAME).orEmpty()
        val viewModel = hiltViewModel<CityViewModel>()
        val data = viewModel.data.collectAsStateWithLifecycle()
        val connectionState by viewModel.connectionState
            .observe()
            .collectAsState(
                initial = ConnectivityObserver.Status.Available
            )

        LaunchedEffect(null) {
            viewModel.onEvent(
                CityViewModel.Event.GetWeather(cityName = cityName, openWeatherUnit = "metric")
            )
        }

        LaunchedEffect(key1 = connectionState) {
            viewModel.onEvent(CityViewModel.Event.ConnectionState(connectionState))
            if(connectionState == ConnectivityObserver.Status.Available) {
                viewModel.onEvent(
                    CityViewModel.Event.GetWeather(cityName = cityName, openWeatherUnit = "metric")
                )
            }
        }

        CityDetailScreen(
            cityName = cityName,
            data = data.value,
            onBackPressed = onBackPressed
        )
    }
}
