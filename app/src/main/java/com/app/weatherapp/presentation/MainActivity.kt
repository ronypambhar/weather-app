package com.app.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.app.weatherapp.presentation.screens.citydetail.cityDetailNavigation
import com.app.weatherapp.presentation.screens.citydetail.navigateToCityDetail
import com.app.weatherapp.presentation.screens.citylist.CITY_LIST
import com.app.weatherapp.presentation.screens.citylist.citiesNavigation
import com.app.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = CITY_LIST
                    ) {
                        citiesNavigation(
                            navigateToCityDetail = { navController.navigateToCityDetail(it) }
                        )
                        cityDetailNavigation(
                            onBackPressed = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
