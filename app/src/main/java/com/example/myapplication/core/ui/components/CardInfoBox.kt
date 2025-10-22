package com.example.myapplication.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.NavTextInactiveDark
import com.example.myapplication.core.ui.theme.PaddingQuarter

@Composable
fun CardInfoBox(title: String? = null, text: String) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth().padding(vertical = PaddingQuarter),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(1.dp, NavTextInactiveDark),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (!title.isNullOrEmpty()) {
                Text(
                    text = title,
                    textAlign = TextAlign.Start,
                    style = AppTypography.bodyMedium.copy(
                        fontWeight = FontWeight.W600
                    ),
                    lineHeight = 24.sp
                )
            }
            Text(
                text = text,
                textAlign = TextAlign.Start,
                style = AppTypography.bodySmall,
                lineHeight = 24.sp
            )
        }
    }
}