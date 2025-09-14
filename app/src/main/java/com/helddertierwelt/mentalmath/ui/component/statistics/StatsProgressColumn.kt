/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.ui.component.statistics

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
fun StatsProgressColumn(
    modeEnabled: Boolean,
    fontSize: TextUnit,
    iconSize: Dp,
    totalAnswers: Int,
    activeTime: Long,
) {

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var iconId = R.drawable.rounded_timer_24
        var progressText = formatTime(activeTime)
        if (!modeEnabled) {
            iconId = R.drawable.tag
            progressText = String.format("%sex", totalAnswers.toString())
        }
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            painter = painterResource(iconId),
            contentDescription = "progressIcon",
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = progressText,
            fontSize = fontSize,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

fun formatTime(ms: Long): String {
    val seconds = ms / 1000
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60

    return buildString {
        if (hours > 0) {
            append("$hours:")
            append(minutes.toString().padStart(2, '0'))
            append(":")
            append(remainingSeconds.toString().padStart(2, '0'))
        } else if (minutes > 0) {
            append("$minutes:")
            append(remainingSeconds.toString().padStart(2, '0'))
        } else {
            append("${remainingSeconds}s")
        }
    }
}
