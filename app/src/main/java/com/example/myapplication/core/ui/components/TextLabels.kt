package com.example.myapplication.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HeadingText(text: String, isCentered: Boolean) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        textAlign = if (isCentered) TextAlign.Center else TextAlign.Start,
        text = text,
        maxLines = 1,
        style = MaterialTheme.typography.headlineMedium
    )
}
