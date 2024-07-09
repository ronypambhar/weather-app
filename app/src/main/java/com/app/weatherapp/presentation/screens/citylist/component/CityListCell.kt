package com.app.weatherapp.presentation.screens.citylist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.weatherapp.R
import com.app.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun CityListCell(cityName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                headlineColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            headlineContent = {
                Text(text = cityName, style = MaterialTheme.typography.titleLarge)
            },
            leadingContent = {
                Icon(
                    Icons.Filled.Place,
                    contentDescription = null
                )
            },
            trailingContent = {
                Icon(
                    Icons.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.cd_navigate_to_detail),
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListCellPreview() {
    WeatherAppTheme {
        CityListCell(cityName = "Stockholm", onClick = {})
    }
}
