package com.ag.kopfrechner.ui.component.game

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavController
import com.ag.kopfrechner.viewmodel.GameViewModel
import com.ag.kopfrechner.viewmodel.SettingsViewModel

@Composable
fun CountdownTimer(
    fontSize: TextUnit,
    navController: NavController,
    settingsViewModel: SettingsViewModel,
    gameViewModel: GameViewModel
) {

    var timeLeftInSeconds = settingsViewModel.settingsState.value.limit.toInt() * 60 - gameViewModel.gamesState.value.activeTime
    var formattedTime by remember { mutableStateOf(formatTime(timeLeftInSeconds)) }

    LaunchedEffect(gameViewModel.gamesState.value.activeTime) {
        val updatedTimeLeft = settingsViewModel.settingsState.value.limit.toInt() * 60 - gameViewModel.gamesState.value.activeTime
        formattedTime = formatTime(updatedTimeLeft)
        if (updatedTimeLeft <= 0) {
            gameViewModel.setEndTimestamp()
            gameViewModel.pauseTimer()
            navController.navigate("statistics") {
                popUpTo("game") { inclusive = true }
                popUpTo("settings") { inclusive = true }
            }
        }
    }

    Text(
        text = formattedTime,
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = fontSize
    )
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    val minutesString = if (minutes < 10) "0$minutes" else "$minutes"
    val secondsString = if (remainingSeconds < 10) "0$remainingSeconds" else "$remainingSeconds"
    return "$minutesString:$secondsString"
}