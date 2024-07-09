package com.app.weatherapp.presentation.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun LoaderView(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .testTag("LoaderView")
    )
}
