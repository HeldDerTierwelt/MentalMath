/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.ag.kopfrechner.ui.component.settings

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit

@Composable
fun TextLabel(text: String, fontSize: TextUnit) {

    Text(
        text = text,
        fontSize = fontSize,
        color = MaterialTheme.colorScheme.onPrimary,
        lineHeight = fontSize
    )
}