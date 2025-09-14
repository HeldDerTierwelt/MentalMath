/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.helddertierwelt.mentalmath.viewmodel.ThemeMode

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

    onPrimary = Color(0xFF3C3F41),
    onSecondary = Color(0xFFF3F3F3),
    onTertiary = Color(0xFF3C3F41),

    onBackground = Color(0xFF3C3F41),
    onSurface = Color(0xFF3C3F41),
)

val yellow = Color(0xFFFBC02D)
val green = Color(0xFF39A241)
val blue = Color(0xFF1976D2)
val red = Color(0xFFD34747)

@Composable
fun MentalMathTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}