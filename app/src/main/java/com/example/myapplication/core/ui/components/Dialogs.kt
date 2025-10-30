package com.example.myapplication.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.BasicDialogButtonHeight
import com.example.myapplication.core.ui.theme.CornerRadiusDialog
import com.example.myapplication.core.ui.theme.Padding24dp
import com.example.myapplication.core.ui.theme.onPrimaryLight
import com.example.myapplication.core.ui.theme.onSurfaceLight
import com.example.myapplication.core.ui.theme.surfaceContainerLowestLight
import com.example.myapplication.core.ui.theme.tertiaryContainerLight

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


@Composable
fun SimpleIconDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    dismissButtonText: String,
    icon: ImageVector,
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
                        .padding(Padding24dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        icon,
                        contentDescription = stringResource(R.string.success_icon_dialog_content_descr),
                        modifier = Modifier.size(24.dp),
                        tint = onSurfaceLight
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Padding24dp),
                        color = onSurfaceLight,
                        textAlign = TextAlign.Center,
                        text = dialogTitle,
                        style = AppTypography.headlineSmall.copy(
                            fontWeight = FontWeight.W400
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
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
                    }
                }
            }
        }
    }
}

@Composable
fun ExceededCreditDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    dismissButtonText: String,
    icon: ImageVector,
    dialogText: String
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
                        .padding(Padding24dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        color = onSurfaceLight,
                        textAlign = TextAlign.Start,
                        text = dialogTitle,
                        style = AppTypography.bodyMedium.copy(
                            fontWeight = FontWeight.W600
                        )
                    )
                    Icon(
                        icon,
                        contentDescription = stringResource(R.string.success_icon_dialog_content_descr),
                        modifier = Modifier.size(48.dp),
                        tint = tertiaryContainerLight
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Padding24dp),
                        color = onSurfaceLight,
                        textAlign = TextAlign.Start,
                        text = dialogText,
                        style = AppTypography.bodyMedium.copy(
                            fontWeight = FontWeight.W600
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
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
                    }
                }
            }
        }
    }
}