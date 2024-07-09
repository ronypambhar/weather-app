package com.app.weatherapp.presentation.screens.citylist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.app.weatherapp.R
import com.app.weatherapp.presentation.components.ErrorView
import com.app.weatherapp.presentation.components.LoaderView
import com.app.weatherapp.presentation.components.NoInternetView
import com.app.weatherapp.presentation.screens.citylist.component.CityListCell
import com.app.weatherapp.presentation.utils.UiState
import com.app.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun CitiesScreen(
    data: CitiesViewModel.Data,
    navigateToCityDetail: (cityName: String) -> Unit
) {
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentHeight(),
                text = stringResource(R.string.cities),
                style = MaterialTheme.typography.headlineMedium
            )
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
                    LazyColumn(
                        modifier = Modifier
                            .padding(8.dp)
                            .testTag("SuccessView")
                    ) {
                        items(items = data.cities) { cityName ->
                            CityListCell(
                                cityName = cityName,
                                onClick = { navigateToCityDetail(cityName) }
                            )
                        }
                    }
                }

                UiState.ERROR -> {
                    ErrorView(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = stringResource(R.string.no_cities_found)
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
fun CitiesScreenPreview() {
    WeatherAppTheme {
        CitiesScreen(
            data = CitiesViewModel.Data(
                cities = listOf("Stockholm", "London"),
                uiState = UiState.SUCCESS
            ),
            navigateToCityDetail = {}
        )
    }
}

