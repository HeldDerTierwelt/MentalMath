/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.ag.kopfrechner.ui.component.game

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ag.kopfrechner.viewmodel.GameViewModel
import kotlin.math.ceil

@Composable
fun CountdownTimer(
    fontSize: TextUnit,
    navController: NavController,
    time: Long,
    gameViewModel: GameViewModel
) {

    var timeLeft = time - gameViewModel.gamesState.value.activeTime
    var formattedTime by remember { mutableStateOf(formatTime(timeLeft)) }

    LaunchedEffect(timeLeft) {
        formattedTime = formatTime(timeLeft)
        if (timeLeft <= 0) {
            gameViewModel.endGame()
            navController.navigate("statistics") {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Text(
        text = formattedTime,
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = fontSize,
        modifier = Modifier.padding(end = 16.dp)
    )
}

fun formatTime(milliseconds: Long): String {
    val seconds = ceil(milliseconds / 1000.0).toLong()
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60
    return buildString {
        if (minutes > 0) {
            append("$minutes:")
            append(remainingSeconds.toString().padStart(2, '0'))
        } else {
            append("$remainingSeconds")
        }
    }
}