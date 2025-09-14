/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.presentation.component.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.helddertierwelt.mentalmath.R

@Composable
fun SettingsModeColumn(
    modeEnabled: Boolean,
    fontSize: TextUnit,
    iconSize: Dp,
    limit: Int
) {

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var iconId = R.drawable.tag
        var limitText = String.format("%s", (limit * 10).toString())
        if (!modeEnabled) {
            iconId = R.drawable.round_hourglass_bottom_24
            limitText =
                String.format("%s:00", limit)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            painter = painterResource(iconId),
            contentDescription = "modeIcon",
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = limitText,
            fontSize = fontSize,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}