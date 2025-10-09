package com.example.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Roboto"),
        fontProvider = provider,
        weight = FontWeight.Medium
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Roboto"),
        fontProvider = provider,
        weight = FontWeight.Medium
    )
)

val headlineFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Roboto"),
        fontProvider = provider,
        weight = FontWeight.Bold
    )
)

val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = headlineFontFamily),
    headlineMedium = baseline.headlineMedium.copy(
        fontSize = 32.sp,
        fontFamily = headlineFontFamily,
        letterSpacing = 0.5.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = headlineFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
    titleMedium = baseline.titleMedium.copy(
        fontSize = 16.sp,
        fontFamily = displayFontFamily,
        letterSpacing = 0.15.sp,
        lineHeight = 24.sp
    ),
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
    bodyLarge = baseline.bodyLarge.copy(
        fontSize = 16.sp,
        fontFamily = bodyFontFamily,
        letterSpacing = 0.5.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
    bodySmall = baseline.bodySmall.copy(
        fontSize = 12.sp,
        fontFamily = bodyFontFamily,
        letterSpacing = 0.4.sp,
        lineHeight = 16.sp
    ),
    labelLarge = baseline.labelLarge.copy(
        fontSize = 36.sp,
        fontFamily = bodyFontFamily,
        letterSpacing = 0.1.sp,
        lineHeight = 20.sp
    ),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
)