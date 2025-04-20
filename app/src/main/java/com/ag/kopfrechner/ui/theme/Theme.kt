package com.ag.kopfrechner.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF3C3F41), // button and topBar
    secondary = Color(0xFF9B9B9B),
    tertiary = Color(0xFF5A5A5A),

    background = Color(0xFF1E1E1E),
    surface = Color(0xFF2A2A2A),

    onPrimary = Color(0xFFEDEDED),
    onSecondary = Color(0xFF3C3F41),
    onTertiary = Color(0xFFEDEDED),

    onBackground = Color(0xFFEDEDED),
    onSurface = Color(0xFFEDEDED)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFFFFFF),
    secondary = Color(0xFF9B9B9B),
    tertiary = Color(0xFF5A5A5A),

    background = Color(0xFFE5E5E5),
    surface = Color(0xFFFFFFFF),

    onPrimary = Color(0xFF1E1E1E),
    onSecondary = Color(0xFFF3F3F3),
    onTertiary = Color(0xFFEDEDED),

    onBackground = Color(0xFF1E1E1E),
    onSurface = Color(0xFF343434)
)

val yellow = Color(0xFFFBC02D)
val green = Color(0xFF39A241)
val blue = Color(0xFF1976D2)
val red = Color(0xFFD34747)

val softYellow = Color(0xFFFFE965)
val softGreen = Color(0xFF77CC7A)
val softBlue = Color(0xFF64B5F6)
val softRed = Color(0xFFE56F6F)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}