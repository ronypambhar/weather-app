package com.app.weatherapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorView(modifier: Modifier, text: String) {
    Text(
        modifier = modifier
            .testTag("ErrorMessageView")
            .padding(16.dp),
        text = text,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )
}
