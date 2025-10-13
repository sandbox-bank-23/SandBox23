@file:Suppress("complexity:CognitiveComplexMethod", "complexity:CyclomaticComplexMethod")

package com.example.myapplication.core.ui.components

import android.util.DisplayMetrics
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.core.ui.theme.CornerRadiusSmall
import com.example.myapplication.core.ui.theme.InputFieldHeight
import com.example.myapplication.core.ui.theme.InputFieldThickBorder
import com.example.myapplication.core.ui.theme.InputFieldThinBorder
import com.example.myapplication.core.ui.theme.Neutral30
import com.example.myapplication.core.ui.theme.Padding12dp
import com.example.myapplication.core.ui.theme.PaddingBase
import com.example.myapplication.core.ui.theme.PaddingQuarter
import com.example.myapplication.core.ui.theme.SuccessGreen
import com.example.myapplication.core.ui.theme.SupportingTextHeight
import com.example.myapplication.core.ui.theme.TrailingIconSize
import com.example.myapplication.core.ui.theme.ZeroPadding

@Suppress("DEPRECATION", "Indentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(
    label: String,
    supportingText: String = "",
    isPassword: Boolean,
    isError: Boolean,
    isSuccess: Boolean
) {
    // Статические переменные
    val context = LocalResources.current
    val displayMetrics: DisplayMetrics = context.displayMetrics
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val iconInteractionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    val enabled = !isSuccess
    val singleLine = true
    val keyboardController = LocalSoftwareKeyboardController.current
    val isPasswordVisibilityPressed by iconInteractionSource.collectIsPressedAsState()

    // Расчётные переменные
    val cursorColor = MaterialTheme.colorScheme.primary
    val borderColor = when {
        isError -> MaterialTheme.colorScheme.error
        isFocused -> MaterialTheme.colorScheme.primary
        isSuccess -> SuccessGreen
        else -> MaterialTheme.colorScheme.outline
    }
    val labelColor = when {
        isError -> MaterialTheme.colorScheme.error
        isFocused -> MaterialTheme.colorScheme.primary
        isSuccess -> Neutral30
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    val iconColor = when {
        isError -> MaterialTheme.colorScheme.error
        isSuccess -> SuccessGreen
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    val borderWidth =
        if (!isFocused && text.isEmpty()) InputFieldThinBorder else InputFieldThickBorder
    val labelHeight: Float =
        MaterialTheme.typography.bodySmall.lineHeight.value.toInt() *
            (displayMetrics.scaledDensity / displayMetrics.density)
    val labelSpacerHeight: Int = when (label.isNotEmpty()) {
        true -> {
            (
                (
                    labelHeight -
                        (
                            InputFieldThinBorder.value.toInt() +
                                InputFieldThickBorder.value.toInt()
                            ) / 2
                    ) / 2
                ).toInt()
        }

        false -> 0
    }
    val totalFieldHeight =
        labelSpacerHeight.dp +
            InputFieldHeight +
            if (supportingText.isNotEmpty()) SupportingTextHeight else ZeroPadding
    val trailingIcon: @Composable () -> Unit = {
        when {
            isPassword && !isError -> {
                Box(
                    modifier = Modifier.Companion
                        .size(TrailingIconSize)
                        .clickable(
                            onClick = { },
                            interactionSource = iconInteractionSource,
                            indication = null
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        tint = iconColor,
                        imageVector =
                        if (isPasswordVisibilityPressed) {
                            Icons.Outlined.Visibility
                        } else {
                            Icons.Outlined.VisibilityOff
                        },
                        contentDescription = stringResource(R.string.show_password)
                    )
                }
            }

            isError || isSuccess -> {
                Box(
                    modifier = Modifier.Companion.size(TrailingIconSize),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        tint = iconColor,
                        imageVector = if (isError) Icons.Filled.Info else Icons.Outlined.Check,
                        contentDescription =
                        if (isError) {
                            stringResource(R.string.error)
                        } else {
                            stringResource(R.string.success)
                        }
                    )
                }
            }

            else -> Unit
        }
    }

    BasicTextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth()
            .height(totalFieldHeight)
            .onFocusChanged {
                isFocused = it.isFocused
            },
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine,
        cursorBrush = SolidColor(cursorColor),
        textStyle =
        MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        visualTransformation =
        if (isPasswordVisibilityPressed || !isPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        decorationBox = { innerTextField ->
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(labelSpacerHeight.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(InputFieldHeight)
                        .border(
                            borderWidth,
                            borderColor,
                            RoundedCornerShape(CornerRadiusSmall)
                        )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(start = PaddingBase)
                                .weight(1F),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            innerTextField()
                        }
                        trailingIcon()
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SupportingTextHeight),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        modifier = Modifier.padding(start = PaddingBase),
                        text = supportingText,
                        style = MaterialTheme.typography.bodySmall.copy(color = labelColor)
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.Companion.width(Padding12dp))
                Box(
                    modifier = Modifier
                        .padding(horizontal = ZeroPadding)
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = PaddingQuarter),
                        text = label,
                        style = MaterialTheme.typography.bodySmall.copy(color = labelColor)
                    )
                }
            }
        }
    )
}