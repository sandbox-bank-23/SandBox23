package com.example.myapplication.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.ButtonMainHeight
import com.example.myapplication.core.ui.theme.DisabledButtonText
import com.example.myapplication.core.ui.theme.onTertiaryLight
import com.example.myapplication.core.ui.theme.tertiaryLight

@Composable
fun PrimaryButton(label: String, isEnabled: Boolean = true, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonMainHeight),
        onClick = onClick,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = DisabledButtonText,
            disabledContainerColor = MaterialTheme.colorScheme.outlineVariant
        ),
        content = {
            Text(
                text = label,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
        }
    )
}

@Composable
fun SecondaryButton(label: String, isEnabled: Boolean = true, onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonMainHeight),
        onClick = onClick,
        enabled = isEnabled,
        content = {
            Text(
                text = label,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    )
}

@Composable
fun CloseButton(label: String, isEnabled: Boolean = true, onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonMainHeight),
        onClick = onClick,
        enabled = isEnabled,
        border = BorderStroke(color = onTertiaryLight, width = 1.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = tertiaryLight
        ),
        content = {
            Text(
                text = label,
                maxLines = 1,
                style = AppTypography.titleMedium.copy(
                    color = onTertiaryLight
                )
            )
        }
    )
}
