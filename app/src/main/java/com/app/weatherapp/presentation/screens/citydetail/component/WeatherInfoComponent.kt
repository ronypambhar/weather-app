package com.app.weatherapp.presentation.screens.citydetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.domain.models.CityWeatherData
import com.app.weatherapp.R
import com.app.weatherapp.ui.theme.WeatherAppTheme
import kotlin.math.roundToInt

@Composable
fun WeatherInfoComponent(weatherData: CityWeatherData.WeatherData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = weatherData.iconUrl,
            contentDescription = stringResource(R.string.cd_weather_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp),
        ) {

            Text(
                text = weatherData.main,
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = weatherData.temp.roundToInt().toString() + "°C",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeatherInfoComponent() {
    WeatherAppTheme {
        WeatherInfoComponent(
            weatherData = CityWeatherData.WeatherData(
                name = "Stockholm",
                icon = "10d",
                main = "Sunny"
            )
        )
    }
}
