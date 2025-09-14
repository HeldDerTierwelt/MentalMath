/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.presentation.component.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.helddertierwelt.mentalmath.presentation.theme.green
import com.helddertierwelt.mentalmath.presentation.viewmodel.SettingsViewModel

@Composable
fun StartButton(
    buttonTextId: Int,
    icon: ImageVector,
    settingsViewModel: SettingsViewModel,
    onClick: () -> Unit,
    iconSize: Dp,
    size: Dp,
    fontSize: TextUnit
) {

    val settingsState = settingsViewModel.settingsState.value
    val isEnabled: Boolean = settingsState.isPlusEnabled ||
            settingsState.isMinusEnabled ||
            settingsState.isMultiplyEnabled ||
            settingsState.isDivideEnabled

    Button(
        enabled = isEnabled,
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(size)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Description",
                modifier = Modifier.size(iconSize),
                tint = if (isEnabled) green else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            )
            Text(
                text = stringResource(buttonTextId),
                fontSize = fontSize,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}