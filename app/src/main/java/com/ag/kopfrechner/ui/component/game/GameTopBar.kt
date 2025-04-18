package com.ag.kopfrechner.ui.component.game

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ag.kopfrechner.ui.theme.red
import com.ag.kopfrechner.ui.theme.softRed
import com.ag.kopfrechner.viewmodel.GameViewModel
import com.ag.kopfrechner.viewmodel.SettingsViewModel
import kotlin.math.min
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(
    navController: NavController,
    settingsViewModel: SettingsViewModel,
    gameViewModel: GameViewModel,
    iconSize: Dp,
    fontSize: TextUnit
) {

    val limit = settingsViewModel.settingsState.value.limit
    val totalTasks = (limit * 10).roundToInt()
    val answeredTasks = min(gameViewModel.gamesState.value.totalAnswers + 1, totalTasks)

    TopAppBar(

        title = {
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate("settings") {
                        popUpTo("game") { inclusive = true }
                        popUpTo("settings") { inclusive = true }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "",
                    modifier = Modifier.size(iconSize),
                    tint = if (isSystemInDarkTheme()) softRed else red
                )
            }
        },
        actions = {
            if (settingsViewModel.settingsState.value.isModeEnabled) {
                Text(
                    text = "$answeredTasks/$totalTasks",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = fontSize,
                    modifier = Modifier.padding(end = 16.dp)
                )
            } else {
                CountdownTimer(
                    initialTimeInSeconds = (limit * 60).toInt(),
                    fontSize = fontSize,
                    navController = navController,
                    gameViewModel = gameViewModel
                )
            }
        },
    )
}