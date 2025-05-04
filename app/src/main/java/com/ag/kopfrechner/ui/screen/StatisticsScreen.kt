package com.ag.kopfrechner.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ag.kopfrechner.R
import com.ag.kopfrechner.ui.component.statistics.DoneButton
import com.ag.kopfrechner.ui.component.statistics.StatisticsTopBar
import com.ag.kopfrechner.ui.theme.green
import com.ag.kopfrechner.ui.theme.red
import com.ag.kopfrechner.ui.theme.yellow
import com.ag.kopfrechner.viewmodel.GameViewModel
import com.ag.kopfrechner.viewmodel.SettingsViewModel
import java.util.Locale

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
    val resultFontSize = (0.064f * screenWidth.value).sp
    val iconSize = (0.032f * screenHeight.value).dp
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

                Box(modifier = Modifier
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
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        gameViewModel.gamesState.value.tasks.forEach { task ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                val exercise = String.format(
                                    Locale.ROOT,
                                    "%d %s %d = %d",
                                    task.operand1,
                                    stringResource(task.operator),
                                    task.operand2,
                                    task.correctResult
                                )
                                Text(
                                    text = exercise,
                                    fontSize = resultFontSize
                                )

                                val isCorrect =
                                    if (!task.userInput.isEmpty()) task.correctResult == task.userInput.toInt() else null
                                when (isCorrect) {

                                    true -> Icon(
                                        painter = painterResource(R.drawable.round_check_circle_24),
                                        contentDescription = "Correct",
                                        modifier = Modifier
                                            .size(iconSize)
                                            .align(Alignment.CenterVertically),
                                        tint = green
                                    )

                                    false -> Row(horizontalArrangement = Arrangement.End) {
                                        Text(
                                            text = task.userInput,
                                            color = red,
                                            fontSize = resultFontSize
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            painter = painterResource(R.drawable.round_cancel_24),
                                            contentDescription = "Skipped",
                                            modifier = Modifier
                                                .size(iconSize)
                                                .align(Alignment.CenterVertically),
                                            tint = red
                                        )
                                    }

                                    null -> Icon(
                                        painter = painterResource(R.drawable.round_cancel_24),
                                        contentDescription = "Skipped",
                                        modifier = Modifier
                                            .size(iconSize)
                                            .align(Alignment.CenterVertically),
                                        tint = yellow
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                        }
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
