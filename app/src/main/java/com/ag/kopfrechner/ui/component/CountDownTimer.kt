package com.ag.kopfrechner.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun CountdownTimer(
    initialTimeInSeconds: Int,
    fontSize: TextUnit,
    navController: NavController
) {
    var timeLeftInSeconds by remember { mutableIntStateOf(initialTimeInSeconds) }
    var formattedTime by remember { mutableStateOf(formatTime(timeLeftInSeconds)) }

    LaunchedEffect(timeLeftInSeconds) {
        while (timeLeftInSeconds > 0) {
            delay(1000)
            timeLeftInSeconds -= 1
            formattedTime = formatTime(timeLeftInSeconds)
        }
        navController.navigate("settings")
    }

    // Text für die Countdown-Anzeige
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