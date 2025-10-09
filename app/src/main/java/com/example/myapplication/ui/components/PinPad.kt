package com.example.myapplication.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.ui.theme.PinCodeProgressBarDotSize
import com.example.myapplication.ui.theme.PinCodeProgressBarDotStrokeWidth
import com.example.myapplication.ui.theme.PinCodeProgressBarWidth
import com.example.myapplication.ui.theme.PinPadBackgroundColor
import com.example.myapplication.ui.theme.PinPadTextColor
import com.example.myapplication.ui.theme.PinpadButtonDiameter
import com.example.myapplication.ui.theme.PinpadWidth

@Suppress("MagicNumber")
@Composable
fun PinPad(
    isFingerprintEnabled: Boolean,
    onDigitClick: (digit: Int) -> Unit,
    onBackspaceClick: () -> Unit,
    onFingerprintClick: () -> Unit
) {
    val buttonsSpace = (PinpadWidth - PinpadButtonDiameter * 3) / 2
    Column(modifier = Modifier.width(PinpadWidth)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            for (i in 1..3) PinPadDigitButton(i, onDigitClick)
        }
        Spacer(modifier = Modifier.height(buttonsSpace))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            for (i in 4..6) PinPadDigitButton(i, onDigitClick)
        }
        Spacer(modifier = Modifier.height(buttonsSpace))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            for (i in 7..9) PinPadDigitButton(i, onDigitClick)
        }
        Spacer(modifier = Modifier.height(buttonsSpace))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            PinPadFingerprintButton(isFingerprintEnabled, onFingerprintClick)
            PinPadDigitButton(onClick = onDigitClick)
            PinPadBackspaceButton(onBackspaceClick)
        }
    }
}

@Suppress("MagicNumber")
@Composable
fun PinPadDigitButton(number: Int = 0, onClick: (digit: Int) -> Unit) {
    if (number in 0..9) {
        Box(modifier = Modifier.size(PinpadButtonDiameter)) {
            FilledTonalButton(
                modifier = Modifier.fillMaxSize(),
                onClick = { onClick(number) },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = PinPadBackgroundColor,
                    contentColor = PinPadTextColor
                ),
                content = {
                    Text(
                        text = number.toString(),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            )
        }
    }
}

@Composable
fun PinPadBackspaceButton(onClick: () -> Unit) {
    Box(modifier = Modifier.size(PinpadButtonDiameter)) {
        IconButton(
            modifier = Modifier.fillMaxSize(),
            onClick = onClick,
            content = {
                Icon(
                    painter = painterResource(R.drawable.back),
                    tint = Color.Unspecified,
                    contentDescription = stringResource(R.string.backspace)
                )
            }
        )
    }
}

@Composable
fun PinPadFingerprintButton(isFingerprintEnabled: Boolean, onClick: () -> Unit) {
    Box(modifier = Modifier.size(PinpadButtonDiameter)) {
        if (isFingerprintEnabled) {
            IconButton(
                modifier = Modifier.fillMaxSize(),
                onClick = onClick,
                content = {
                    Icon(
                        painter = painterResource(R.drawable.fingerprint),
                        tint = Color.Unspecified,
                        contentDescription = stringResource(R.string.fingerprint)
                    )
                }
            )
        }
    }
}


@Composable
fun PinCodeProgressBar(digitsEntered: Int, digitsTotal: Int, isError: Boolean) {
    Row(
        modifier = Modifier.width(PinCodeProgressBarWidth),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val currentColor = if (isError) {
            MaterialTheme.colorScheme.error
        } else if (digitsTotal == digitsEntered) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.secondary
        }
        for (i in 1..digitsTotal) {
            if (i > digitsEntered) {
                DotStroked(currentColor)
            } else {
                DotFilled(currentColor)
            }
        }
    }
}

@Composable
fun DotFilled(color: Color) {
    Canvas(modifier = Modifier.size(PinCodeProgressBarDotSize)) {
        drawCircle(
            color = color,
            radius = (PinCodeProgressBarDotSize / 2).toPx(),
            center = center
        )
    }
}

@Composable
fun DotStroked(color: Color) {
    Canvas(modifier = Modifier.size(PinCodeProgressBarDotSize)) {
        drawCircle(
            color = color,
            radius = ((PinCodeProgressBarDotSize - PinCodeProgressBarDotStrokeWidth) / 2).toPx(),
            center = center,
            style = Stroke(width = PinCodeProgressBarDotStrokeWidth.toPx())
        )
    }
}
