/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.ui.component.game

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.helddertierwelt.mentalmath.ui.theme.green
import com.helddertierwelt.mentalmath.ui.theme.yellow

@Composable
fun EnterButton(
    iconId1: Int,
    iconId2: Int,
    onClick: () -> Unit,
    iconSize: Dp,
    isToggled: Boolean,
    size: Dp,
) {

    val skipColor = yellow
    val enterColor = green
    val icon = if (isToggled) iconId1 else iconId2
    val containerColor = if (isToggled) skipColor else enterColor

    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(size),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Enter",
            modifier = Modifier.size(iconSize),
        )
    }
}