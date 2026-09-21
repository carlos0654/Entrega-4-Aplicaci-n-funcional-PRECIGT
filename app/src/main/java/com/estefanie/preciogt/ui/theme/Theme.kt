package com.estefanie.preciogt.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = OrangeLight,
    secondary = OrangeContainer,
    background = DarkSurface,
    surface = DarkSurface
)

private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = AppSurface,
    primaryContainer = OrangeContainer,
    onPrimaryContainer = OrangeDark,
    secondary = BlueGray,
    background = AppBackground,
    surface = AppSurface,
    onSurface = Navy,
    outline = AppOutline
)

@Composable
fun PrecioGtTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme && !dynamicColor) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
