package com.example.myapplication.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

val AppFontFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_semibold, FontWeight.Bold)
)

val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = AppFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = AppFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = AppFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = AppFontFamily),
    headlineMedium = baseline.headlineMedium.copy(
        fontSize = 32.sp,
        fontFamily = AppFontFamily,
        letterSpacing = 0.5.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = AppFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = AppFontFamily),
    titleMedium = baseline.titleMedium.copy(
        fontSize = 16.sp,
        fontFamily = AppFontFamily,
        letterSpacing = 0.15.sp,
        lineHeight = 24.sp
    ),
    titleSmall = baseline.titleSmall.copy(fontFamily = AppFontFamily),
    bodyLarge = baseline.bodyLarge.copy(
        fontSize = 16.sp,
        fontFamily = AppFontFamily,
        letterSpacing = 0.5.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = AppFontFamily),
    bodySmall = baseline.bodySmall.copy(
        fontSize = 12.sp,
        fontFamily = AppFontFamily,
        letterSpacing = 0.4.sp,
        lineHeight = 16.sp
    ),
    labelLarge = baseline.labelLarge.copy(
        fontSize = 36.sp,
        fontFamily = AppFontFamily,
        letterSpacing = 0.1.sp,
        lineHeight = 20.sp
    ),
    labelMedium = baseline.labelMedium.copy(fontFamily = AppFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = AppFontFamily)
)