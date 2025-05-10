package com.ag.kopfrechner.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ag.kopfrechner.R
import com.ag.kopfrechner.ui.component.statistics.DoneButton
import com.ag.kopfrechner.ui.component.statistics.SettingsAndStatsCard
import com.ag.kopfrechner.ui.component.statistics.StatisticsTopBar
import com.ag.kopfrechner.ui.component.statistics.TaskCard
import com.ag.kopfrechner.viewmodel.GameViewModel
import com.ag.kopfrechner.viewmodel.SettingsViewModel

@Composable
fun StatisticsScreen(
    navController: NavController,
    gameViewModel: GameViewModel,
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    val settingsState = settingsViewModel.settingsState.value
    val gameState = gameViewModel.gamesState.value

    // Calculate sizes based on screen size
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val roundButtonSize = (0.09f * screenHeight.value).dp
    val doneButtonFontSize = (0.038f * screenHeight.value).sp
    val doneIconSize = (0.057f * screenHeight.value).dp

    val titleFontSize = (0.032f * screenHeight.value).sp
    val resultFontSize = (0.057f * screenWidth.value).sp
    val operatorFontSize = (0.057f * screenHeight.value).sp
    val taskIconSize = (0.032f * screenHeight.value).dp
    val modeIconSize = (0.048f * screenHeight.value).dp
    val columnPadding = (0.028f * screenHeight.value).dp


    Scaffold(
        topBar = {
            StatisticsTopBar(R.string.game_result, titleFontSize)
        },
        content = { padding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                color = MaterialTheme.colorScheme.background
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(columnPadding),
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start
                    ) {
                        SettingsAndStatsCard(
                            gameViewModel = gameViewModel,
                            settingsViewModel = settingsViewModel,
                            fontSize = resultFontSize,
                            operatorSize = operatorFontSize,
                            iconSize = modeIconSize,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TaskCard(
                            gameViewModel = gameViewModel,
                            resultFontSize = resultFontSize,
                            iconSize = taskIconSize
                        )
                    }
                    DoneButton(
                        buttonTextId = R.string.done,
                        icon = Icons.Rounded.Check,
                        onClick = {
                            gameViewModel.resetGame()
                            navController.navigate("settings") {
                                popUpTo("settings") { inclusive = true }
                                popUpTo("game") { inclusive = true }
                            }
                        },
                        size = roundButtonSize,
                        fontSize = doneButtonFontSize,
                        iconSize = doneIconSize,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
    )
}
