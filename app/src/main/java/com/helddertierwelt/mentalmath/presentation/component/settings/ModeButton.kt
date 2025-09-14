/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.presentation.component.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ModeButton(
    iconId1: Int,
    iconId2: Int,
    isToggled: Boolean,
    onClick: () -> Unit,
    iconSize: Dp,
    size: Dp,
) {
    val icon = if (isToggled) iconId1 else iconId2

    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(size),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Mode",
            modifier = Modifier.size(iconSize)
        )
    }
}