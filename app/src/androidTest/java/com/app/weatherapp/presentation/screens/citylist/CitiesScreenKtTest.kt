package com.app.weatherapp.presentation.screens.citylist

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import com.app.weatherapp.presentation.utils.UiState
import com.app.weatherapp.ui.theme.WeatherAppTheme
import org.junit.Rule
import org.junit.Test

class CitiesScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private fun initView(data: CitiesViewModel.Data) {
        composeTestRule.setContent {
            WeatherAppTheme {
                CitiesScreen(
                    data = data,
                    navigateToCityDetail = { }
                )
            }
        }
    }

    @Test
    fun verify_LoaderView_When_FetchingCities() {
        initView(CitiesViewModel.Data(uiState = UiState.LOADING))
        composeTestRule.onNodeWithTag("LoaderView").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SuccessView").assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorMessageView").assertDoesNotExist()
    }

    @Test
    fun verify_SuccessView_After_FetchingCities() {
        initView(CitiesViewModel.Data(cities = listOf("City1", "City2"), uiState = UiState.SUCCESS))
        composeTestRule.onNodeWithTag("LoaderView").assertDoesNotExist()
        composeTestRule.onNodeWithTag("SuccessView").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ErrorMessageView").assertDoesNotExist()
    }

    @Test
    fun verify_ErrorMessageView_After_FetchingCities() {
        initView(CitiesViewModel.Data(uiState = UiState.ERROR))
        composeTestRule.onNodeWithTag("LoaderView").assertDoesNotExist()
        composeTestRule.onNodeWithTag("SuccessView").assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorMessageView").assertIsDisplayed()
    }

    @Test
    fun verify_NoInternetMessageView_When_ThereIsNoInternet() {
        initView(CitiesViewModel.Data(uiState = UiState.NO_INTERNET))
        composeTestRule.onNodeWithTag("LoaderView").assertDoesNotExist()
        composeTestRule.onNodeWithTag("SuccessView").assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorMessageView").assertDoesNotExist()
        composeTestRule.onNodeWithTag("NoInternetMessageView").assertIsDisplayed()
    }
}
