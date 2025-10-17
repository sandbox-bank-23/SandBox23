package com.example.myapplication.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.BasicDialogButtonHeight
import com.example.myapplication.core.ui.theme.CornerRadiusDialog
import com.example.myapplication.core.ui.theme.Padding24dp
import com.example.myapplication.core.ui.theme.SandBox23Theme
import com.example.myapplication.core.ui.theme.onPrimaryLight
import com.example.myapplication.core.ui.theme.onSurfaceLight
import com.example.myapplication.core.ui.theme.surfaceContainerLowestLight

@Composable
fun NotificationDialog(
    title: String? = null,
    notification: String? = null,
    icon: Int? = null,
    onConfirm: () -> Unit,
    confirmButtonText: String,
    onDismiss: (() -> Unit)?,
    dismissButtonText: String?
) {
    val largeNotification = !title.isNullOrEmpty()

    Dialog(
        onDismissRequest = onConfirm
    ) {
        Card(
            shape = RoundedCornerShape(CornerRadiusDialog),
            colors = CardDefaults.cardColors(
                containerColor = surfaceContainerLowestLight
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Padding24dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (largeNotification) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Padding24dp),
                        color = onSurfaceLight,
                        textAlign = TextAlign.Center,
                        text = title,
                        style = AppTypography.bodyMedium
                    )
                }

                if (icon != null) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Padding24dp),
                        tint = onSurfaceLight
                    )
                }

                if (!notification.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Padding24dp),
                        color = onSurfaceLight,
                        textAlign = TextAlign.Center,
                        text = notification,
                        style = if (largeNotification) AppTypography.bodyMedium else AppTypography.headlineSmall
                    )
                }

                Button(
                    modifier = Modifier
                        .height(BasicDialogButtonHeight)
                        .padding(horizontal = 2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = onPrimaryLight
                    ),
                    onClick = onConfirm
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = confirmButtonText,
                        style = AppTypography.labelLarge.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500
                        )
                    )
                }

                if (onDismiss != null && !dismissButtonText.isNullOrEmpty()) {
                    TextButton(
                        modifier = Modifier
                            .height(BasicDialogButtonHeight)

                            .padding(horizontal = 2.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = onPrimaryLight,
                            contentColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                        onClick = onDismiss,
                    ) {
                        Text(
                            text = dismissButtonText,
                            style = AppTypography.headlineSmall
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NotificationPreview() {
    SandBox23Theme {
        NotificationDialog(
            title = "Titile",
            notification = "Message",
            icon = R.drawable.fingerprint,
            onConfirm = {},
            confirmButtonText = "Закрыть",
            onDismiss = {  },
            dismissButtonText = "",
        )
    }
}

