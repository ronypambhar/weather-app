package com.app.weatherapp.presentation.screens.citydetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.domain.models.CityWeatherData
import com.app.weatherapp.R
import com.app.weatherapp.ui.theme.WeatherAppTheme
import kotlin.math.roundToInt

@Composable
fun WeatherDetailComponent(weatherInfo: CityWeatherData.WeatherData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
            Card(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                ListItem(
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                    headlineContent = {
                        Text(
                            modifier = Modifier.padding(end = 10.dp),
                            text = stringResource(
                                R.string.high_temp,
                                weatherInfo.tempMax.roundToInt()
                            ),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer

                        )
                    }
                )
            }
            Card(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                ListItem(
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                    headlineContent = {
                        Text(
                            text = stringResource(
                                R.string.low_temp,
                                weatherInfo.tempMin.roundToInt()
                            ),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                )
            }
        }
        Row {
            WeatherDetailCard(
                modifier = Modifier
                    .weight(1f),
                label = stringResource(R.string.humidity),
                icon = R.drawable.ic_humidity,
                weatherInfo = stringResource(
                    R.string.humidity_value,
                    weatherInfo.humidity.toString()
                )
            )
            WeatherDetailCard(
                modifier = Modifier
                    .weight(1f),
                label = stringResource(R.string.wind),
                icon = R.drawable.ic_wind,
                weatherInfo = stringResource(
                    R.string.speed_value,
                    weatherInfo.windSpeed.toString()
                )
            )
            WeatherDetailCard(
                modifier = Modifier
                    .weight(1f),
                label = stringResource(R.string.sunrise),
                icon = R.drawable.ic_sunrise,
                weatherInfo = weatherInfo.sunriseTime
            )

        }
        Row {
            WeatherDetailCard(
                modifier = Modifier
                    .weight(1f),
                label = stringResource(R.string.sunset),
                icon = R.drawable.ic_sunset,
                weatherInfo = weatherInfo.sunsetTime
            )
            WeatherDetailCard(
                modifier = Modifier
                    .weight(1f),
                label = stringResource(R.string.visibility),
                icon = R.drawable.ic_visibility,
                weatherInfo = stringResource(
                    R.string.visibility_value,
                    weatherInfo.visibility.toString()
                ),
            )
            WeatherDetailCard(
                modifier = Modifier
                    .weight(1f),
                label = stringResource(R.string.pressure),
                icon = R.drawable.ic_pressure,
                weatherInfo = stringResource(
                    R.string.pressure_value,
                    weatherInfo.pressure.toString()
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeatherDetailComponent() {
    WeatherAppTheme {
        WeatherDetailComponent(
            weatherInfo = CityWeatherData.WeatherData(
                name = "Stockholm",
                icon = "10d",
                main = "Sunny"
            )
        )
    }
}
