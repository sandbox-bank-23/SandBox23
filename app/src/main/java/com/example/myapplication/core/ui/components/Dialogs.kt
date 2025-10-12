package com.example.myapplication.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.BasicDialogButtonHeight
import com.example.myapplication.core.ui.theme.CornerRadiusDialog
import com.example.myapplication.core.ui.theme.Padding24dp
import com.example.myapplication.core.ui.theme.onPrimaryLight
import com.example.myapplication.core.ui.theme.onSurfaceLight
import com.example.myapplication.core.ui.theme.surfaceContainerLowestLight

@Composable
fun BasicDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String? = null,
    dialogMessage: String? = null,
    confirmButtonText: String,
    dismissButtonText: String,
) {
    if (visible) {
        Dialog(
            onDismissRequest = {
                onDismissRequest()
            }
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
                    if (!dialogTitle.isNullOrEmpty()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = Padding24dp),
                            color = onSurfaceLight,
                            textAlign = TextAlign.Center,
                            text = dialogTitle,
                            style = AppTypography.headlineSmall
                        )
                    }
                    if (!dialogMessage.isNullOrEmpty()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = Padding24dp),
                            color = onSurfaceLight,
                            textAlign = TextAlign.Center,
                            text = dialogMessage,
                            style = AppTypography.bodyMedium
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            modifier = Modifier
                                .height(BasicDialogButtonHeight)
                                .padding(horizontal = 2.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = onPrimaryLight,
                                contentColor = MaterialTheme.colorScheme.tertiaryContainer
                            ),
                            onClick = {
                                onDismissRequest()
                            },
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = dismissButtonText,
                                style = AppTypography.labelLarge.copy(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W500
                                )
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
                            onClick = {
                                onConfirmation()
                            }
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
                    }
                }
            }
        }
    }
}