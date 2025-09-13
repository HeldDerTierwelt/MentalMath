package com.ag.kopfrechner.ui.component.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ag.kopfrechner.R
import com.ag.kopfrechner.viewmodel.ThemeMode

@Composable
fun DarkModeToggleRow(
    fontSize: androidx.compose.ui.unit.TextUnit,
    iconSize: Dp,
    currentMode: ThemeMode,
    onModeChange: (ThemeMode) -> Unit
) {
    val icons = listOf(
        R.drawable.dark_mode_24dp,
        R.drawable.light_mode_24dp,
        R.drawable.dark_light_mode_24dp
    )
    val texts = listOf(
        "Dark Mode",
        "Light Mode",
        "System Mode"
    )
    val modes = listOf(ThemeMode.DARK, ThemeMode.LIGHT, ThemeMode.SYSTEM)
    val currentIndex = modes.indexOf(currentMode).coerceAtLeast(0)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .clickable {
                val nextIndex = (currentIndex + 1) % modes.size
                onModeChange(modes[nextIndex])
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        androidx.compose.material3.Icon(
            painter = painterResource(icons[currentIndex]),
            contentDescription = texts[currentIndex],
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.width(8.dp))
        androidx.compose.material3.Text(
            text = texts[currentIndex],
            fontSize = fontSize,
            lineHeight = fontSize
        )
    }
}