package com.phonecompare.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = OnAccent,
    primaryContainer = ElectricBlueSoft,
    onPrimaryContainer = Color(0xFFCFE2FF),
    secondary = Color(0xFF8FB4E8),
    onSecondary = OnAccent,
    background = Charcoal900,
    onBackground = TextPrimary,
    surface = Charcoal800,
    onSurface = TextPrimary,
    surfaceVariant = Charcoal600,
    onSurfaceVariant = TextSecondary,
    surfaceContainer = Charcoal700,
    surfaceContainerHigh = Charcoal600,
    outline = OutlineStrong,
    outlineVariant = OutlineSoft,
    error = ErrorRed,
    onError = OnAccent,
)

private val LightColors = lightColorScheme(
    primary = ElectricBlueDim,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD7E6FF),
    onPrimaryContainer = Color(0xFF07243F),
    background = LightBackground,
    onBackground = TextPrimaryLight,
    surface = LightSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = TextSecondaryLight,
    outline = LightOutline,
    error = Color(0xFFBA1A1A),
)

/**
 * PhoneCompare Material 3 theme.
 *
 * Defaults to the dark "instrument" identity. Dynamic color is intentionally
 * disabled so the brand accent stays consistent across devices.
 */
@Composable
fun PhoneCompareTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = PhoneCompareTypography,
        shapes = PhoneCompareShapes,
        content = content,
    )
}
