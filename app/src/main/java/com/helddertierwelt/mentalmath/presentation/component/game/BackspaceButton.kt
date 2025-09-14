/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.presentation.component.game

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
import com.helddertierwelt.mentalmath.R
import com.helddertierwelt.mentalmath.presentation.theme.red

@Composable
fun BackspaceButton(
    onClick: () -> Unit,
    iconSize: Dp,
    size: Dp,
) {

    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(size),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = red,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.round_backspace_24),
            contentDescription = "Backspace",
            modifier = Modifier.size(iconSize),
        )
    }
}