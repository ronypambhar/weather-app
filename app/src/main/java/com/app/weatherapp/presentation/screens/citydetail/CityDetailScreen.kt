package com.app.weatherapp.presentation.screens.citydetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.domain.models.CityWeatherData
import com.app.weatherapp.R
import com.app.weatherapp.presentation.components.ErrorView
import com.app.weatherapp.presentation.components.LoaderView
import com.app.weatherapp.presentation.components.NoInternetView
import com.app.weatherapp.presentation.screens.citydetail.component.WeatherDetailComponent
import com.app.weatherapp.presentation.screens.citydetail.component.WeatherInfoComponent
import com.app.weatherapp.presentation.utils.UiState
import com.app.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun CityDetailScreen(
    cityName: String,
    data: CityViewModel.Data,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .height(56.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically

            ) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "NavigateBack",
                        modifier = Modifier.size(40.dp)
                    )
                }
                Text(
                    text = cityName,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when (data.uiState) {
                UiState.LOADING -> {
                    LoaderView(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

                UiState.SUCCESS -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("SuccessView")
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        WeatherInfoComponent(data.weatherData)
                        WeatherDetailComponent(data.weatherData)
                    }
                }

                UiState.ERROR -> {
                    val errorStringRes =
                        when (data.errorData) {
                            CityWeatherData.ContactOpenWeatherService ->
                                R.string.contact_open_weather_service

                            CityWeatherData.ExceededFreeLimit ->
                                R.string.exceeded_free_limit_of_the_service

                            CityWeatherData.InvalidAccess ->
                                R.string.invalid_access_to_open_weather_map

                            CityWeatherData.NoCityFound ->
                                R.string.no_city_found

                            CityWeatherData.UnableToReachServer ->
                                R.string.unable_to_reach_server_try_after_some_time

                            else ->
                                R.string.something_went_wrong_please_try_again_later
                        }
                    ErrorView(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = stringResource(errorStringRes)
                    )
                }

                UiState.NO_INTERNET -> {
                    NoInternetView(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CityDetailPreview() {
    WeatherAppTheme {
        CityDetailScreen(
            cityName = "Stockholm",
            data = CityViewModel.Data(uiState = UiState.SUCCESS),
            onBackPressed = {}
        )
    }
}
