package com.example.myapplication.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.core.ui.theme.backgroundDark
import com.example.myapplication.core.ui.theme.backgroundLight
import com.example.myapplication.core.ui.theme.errorContainerDark
import com.example.myapplication.core.ui.theme.errorContainerLight
import com.example.myapplication.core.ui.theme.errorDark
import com.example.myapplication.core.ui.theme.errorLight
import com.example.myapplication.core.ui.theme.inverseOnSurfaceDark
import com.example.myapplication.core.ui.theme.inverseOnSurfaceLight
import com.example.myapplication.core.ui.theme.inversePrimaryDark
import com.example.myapplication.core.ui.theme.inversePrimaryLight
import com.example.myapplication.core.ui.theme.inverseSurfaceDark
import com.example.myapplication.core.ui.theme.inverseSurfaceLight
import com.example.myapplication.core.ui.theme.onBackgroundDark
import com.example.myapplication.core.ui.theme.onBackgroundLight
import com.example.myapplication.core.ui.theme.onErrorContainerDark
import com.example.myapplication.core.ui.theme.onErrorContainerLight
import com.example.myapplication.core.ui.theme.onErrorDark
import com.example.myapplication.core.ui.theme.onErrorLight
import com.example.myapplication.core.ui.theme.onPrimaryContainerDark
import com.example.myapplication.core.ui.theme.onPrimaryContainerLight
import com.example.myapplication.core.ui.theme.onPrimaryDark
import com.example.myapplication.core.ui.theme.onPrimaryLight
import com.example.myapplication.core.ui.theme.onSecondaryContainerDark
import com.example.myapplication.core.ui.theme.onSecondaryContainerLight
import com.example.myapplication.core.ui.theme.onSecondaryDark
import com.example.myapplication.core.ui.theme.onSecondaryLight
import com.example.myapplication.core.ui.theme.onSurfaceDark
import com.example.myapplication.core.ui.theme.onSurfaceLight
import com.example.myapplication.core.ui.theme.onSurfaceVariantDark
import com.example.myapplication.core.ui.theme.onSurfaceVariantLight
import com.example.myapplication.core.ui.theme.onTertiaryContainerDark
import com.example.myapplication.core.ui.theme.onTertiaryContainerLight
import com.example.myapplication.core.ui.theme.onTertiaryDark
import com.example.myapplication.core.ui.theme.onTertiaryLight
import com.example.myapplication.core.ui.theme.outlineDark
import com.example.myapplication.core.ui.theme.outlineLight
import com.example.myapplication.core.ui.theme.outlineVariantDark
import com.example.myapplication.core.ui.theme.outlineVariantLight
import com.example.myapplication.core.ui.theme.primaryContainerDark
import com.example.myapplication.core.ui.theme.primaryContainerLight
import com.example.myapplication.core.ui.theme.primaryDark
import com.example.myapplication.core.ui.theme.primaryLight
import com.example.myapplication.core.ui.theme.scrimDark
import com.example.myapplication.core.ui.theme.scrimLight
import com.example.myapplication.core.ui.theme.secondaryContainerDark
import com.example.myapplication.core.ui.theme.secondaryContainerLight
import com.example.myapplication.core.ui.theme.secondaryDark
import com.example.myapplication.core.ui.theme.secondaryLight
import com.example.myapplication.core.ui.theme.surfaceBrightDark
import com.example.myapplication.core.ui.theme.surfaceBrightLight
import com.example.myapplication.core.ui.theme.surfaceContainerDark
import com.example.myapplication.core.ui.theme.surfaceContainerHighDark
import com.example.myapplication.core.ui.theme.surfaceContainerHighLight
import com.example.myapplication.core.ui.theme.surfaceContainerHighestDark
import com.example.myapplication.core.ui.theme.surfaceContainerHighestLight
import com.example.myapplication.core.ui.theme.surfaceContainerLight
import com.example.myapplication.core.ui.theme.surfaceContainerLowDark
import com.example.myapplication.core.ui.theme.surfaceContainerLowLight
import com.example.myapplication.core.ui.theme.surfaceContainerLowestDark
import com.example.myapplication.core.ui.theme.surfaceContainerLowestLight
import com.example.myapplication.core.ui.theme.surfaceDark
import com.example.myapplication.core.ui.theme.surfaceDimDark
import com.example.myapplication.core.ui.theme.surfaceDimLight
import com.example.myapplication.core.ui.theme.surfaceLight
import com.example.myapplication.core.ui.theme.surfaceVariantDark
import com.example.myapplication.core.ui.theme.surfaceVariantLight
import com.example.myapplication.core.ui.theme.tertiaryContainerDark
import com.example.myapplication.core.ui.theme.tertiaryContainerLight
import com.example.myapplication.core.ui.theme.tertiaryDark
import com.example.myapplication.core.ui.theme.tertiaryLight

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Composable
fun SandBox23Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}