/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.ag.kopfrechner.ui.component.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun StatsColumn(
    fontSize: TextUnit,
    statsText: String,
    iconId: Int,
    iconSize: Dp,
    iconColor: Color,
) {

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            painter = painterResource(iconId),
            contentDescription = "modeIcon",
            modifier = Modifier.size(iconSize),
            tint = iconColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = statsText,
            fontSize = fontSize,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}