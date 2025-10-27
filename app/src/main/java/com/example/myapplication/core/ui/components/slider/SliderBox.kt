package com.example.myapplication.core.ui.components.slider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.core.ui.theme.AppTypography
import kotlin.math.roundToInt

@Composable
fun SliderBox(trackSlider: List<Int>, flagSlider: FlagSlider, dataSlider: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(144.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            text = flagSlider.header,
            textAlign = TextAlign.Start,
            style = AppTypography.bodyMedium.copy(
                fontWeight = FontWeight.W600
            )
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        CustomSlider(
            flagSlider = flagSlider,
            trackSlider = trackSlider,
            dataValue = dataSlider
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = when (flagSlider) {
                    FlagSlider.PERIOD_CREDIT, FlagSlider.PERIOD_DEPOSIT -> trackSlider.first()
                        .toString() + " мес"

                    FlagSlider.LIMIT_CREDIT -> trackSlider.first().toString() + " ₽"
                },
                style = AppTypography.labelLarge.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500
                )
            )
            Text(
                text = when (flagSlider) {
                    FlagSlider.PERIOD_CREDIT, FlagSlider.PERIOD_DEPOSIT -> trackSlider.last()
                        .toString() + " мес"

                    FlagSlider.LIMIT_CREDIT -> trackSlider.last().toString() + " ₽"
                },
                style = AppTypography.labelLarge.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500
                )
            )

        }
    }
}

@Composable
fun CustomSlider(
    flagSlider: FlagSlider,
    trackSlider: List<Int>,
    dataValue: (Int) -> Unit
) {
    var value by remember { mutableFloatStateOf((trackSlider.size / 2).toFloat()) }
    var sliderWidth by remember { mutableFloatStateOf(0f) }
    val safeIndex = value.roundToInt().coerceIn(0, trackSlider.size - 1)
    dataValue(trackSlider[safeIndex])

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .onSizeChanged { size ->
                sliderWidth = size.width.toFloat()
            }
    ) {
        Column {
            ValueLabel(
                value = value,
                sliderWidth = sliderWidth,
                trackSlider = trackSlider,
                flagSlider = flagSlider
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            ViewSlider(
                value = value,
                trackSlider = trackSlider,
                onSliderChange = { newValue -> value = newValue },
            )
        }
    }
}

@Composable
fun ViewSlider(
    value: Float,
    trackSlider: List<Int>,
    onSliderChange: (Float) -> Unit,
) {
    Slider(
        value = value,
        onValueChange = { newValue ->
            onSliderChange(newValue)
        },
        steps = maxOf(0, trackSlider.size - 2),
        valueRange = 0f..(trackSlider.size - 1).toFloat(),
        modifier = Modifier
            .fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.primary,
            activeTrackColor = MaterialTheme.colorScheme.primary,
            inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
}

@Composable
fun ValueLabel(
    value: Float,
    sliderWidth: Float,
    trackSlider: List<Int>,
    flagSlider: FlagSlider,
) {
    var boxWidth by remember { mutableFloatStateOf(0f) }
    Box(
        modifier = Modifier
            .height(40.dp)
            .onSizeChanged { size -> boxWidth = size.width.toFloat() }
            .offset {
                if (sliderWidth == 0f || boxWidth == 0f) {
                    IntOffset(0, 0)
                } else {
                    val fraction = value / (trackSlider.size - 1).toFloat()

                    val offsetX = (fraction * sliderWidth - boxWidth / 2).coerceIn(
                        0f,
                        sliderWidth - boxWidth
                    )
                    IntOffset(offsetX.roundToInt(), 0)
                }
            }
            .background(
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = RoundedCornerShape(100.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        val safeIndex = value.roundToInt().coerceIn(0, trackSlider.size - 1)
        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),

            text = when (flagSlider) {
                FlagSlider.PERIOD_CREDIT, FlagSlider.PERIOD_DEPOSIT -> trackSlider[safeIndex].toString() + " мес"
                FlagSlider.LIMIT_CREDIT -> trackSlider[safeIndex].toString() + " ₽"
            },
            color = MaterialTheme.colorScheme.inverseOnSurface,
            style = AppTypography.labelLarge.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.W500

            )
        )
    }
}
