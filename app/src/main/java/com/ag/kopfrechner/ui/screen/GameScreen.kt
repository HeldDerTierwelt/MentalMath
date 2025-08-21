package com.ag.kopfrechner.ui.screen

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ag.kopfrechner.R
import com.ag.kopfrechner.ui.component.game.BackspaceButton
import com.ag.kopfrechner.ui.component.game.EnterButton
import com.ag.kopfrechner.ui.component.game.GameTopBar
import com.ag.kopfrechner.ui.component.game.MathTaskDisplay
import com.ag.kopfrechner.ui.component.game.NumberButton
import com.ag.kopfrechner.ui.component.game.QuitGamePopUp
import com.ag.kopfrechner.ui.theme.blue
import com.ag.kopfrechner.ui.theme.softBlue
import com.ag.kopfrechner.viewmodel.GameViewModel
import com.ag.kopfrechner.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun GameScreen(
    navController: NavController,
    gameViewModel: GameViewModel,
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    val settingsState = settingsViewModel.settingsState.value
    val gameState = gameViewModel.gamesState.value
    val coroutineScope = rememberCoroutineScope()

    // Calculate sizes based on screen height
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val roundButtonSize = (0.10f * screenHeight.value).dp
    val titleFontSize = (0.032f * screenHeight.value).sp
    val textLabelFontSize = (0.0180f * screenHeight.value).sp
    val numberFontSize = (0.040f * screenHeight.value).sp
    val taskFontSize = (0.057f * screenHeight.value).sp
    val iconSizeClose = (0.057f * screenHeight.value).dp
    val iconSizeDoubleArrow = (0.052f * screenHeight.value).dp
    val iconSizeBackSpace = (0.038f * screenHeight.value).dp
    val columnPadding = (0.028f * screenHeight.value).dp
    val resultLineWidth = (0.30f * screenHeight.value).dp

    Scaffold(
        topBar = {
            Column {
                GameTopBar(
                    navController = navController,
                    settingsViewModel = settingsViewModel,
                    gameViewModel = gameViewModel,
                    iconSize = iconSizeClose,
                    fontSize = numberFontSize
                )
                LinearProgressIndicator(
                    progress = {
                        if (settingsState.isModeEnabled) {
                            gameState.totalAnswers / (settingsState.limit * 10).toFloat()
                        } else {
                            gameState.activeTime / (settingsState.limit * 60)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = if (isSystemInDarkTheme()) softBlue else blue,
                    trackColor = MaterialTheme.colorScheme.background,
                    drawStopIndicator = {},
                    strokeCap = StrokeCap.Butt
                )
            }
        },
        content = { padding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(columnPadding, columnPadding),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        MathTaskDisplay(
                            operand1 = gameState.operand1,
                            operand2 = gameState.operand2,
                            operator = gameState.operator,
                            input = gameState.input,
                            fontSize = taskFontSize,
                            width = resultLineWidth,
                            isCorrect = gameState.isCorrect
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        (1..3).forEach { number ->
                            NumberButton(
                                number = number,
                                onClick = { gameViewModel.appendToInput(number) },
                                size = roundButtonSize,
                                fontSize = numberFontSize
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        (4..6).forEach { number ->
                            NumberButton(
                                number = number,
                                onClick = { gameViewModel.appendToInput(number) },
                                size = roundButtonSize,
                                fontSize = numberFontSize
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        (7..9).forEach { number ->
                            NumberButton(
                                number = number,
                                onClick = { gameViewModel.appendToInput(number) },
                                size = roundButtonSize,
                                fontSize = numberFontSize
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BackspaceButton(
                            onClick = { gameViewModel.removeLastDigit() },
                            size = roundButtonSize,
                            iconSize = iconSizeBackSpace
                        )
                        NumberButton(
                            number = 0,
                            onClick = { gameViewModel.appendToInput(0) },
                            size = roundButtonSize,
                            fontSize = numberFontSize
                        )
                        EnterButton(
                            onClick = {
                                gameViewModel.addTaskResultToList()
                                gameViewModel.checkAnswerAndCount()
                                coroutineScope.launch {
                                    delay(50)
                                    val totalAnswers = gameViewModel.gamesState.value.totalAnswers
                                    val totalTasks = (settingsState.limit * 10).roundToInt()
                                    if (settingsState.isModeEnabled && totalAnswers == totalTasks) {
                                        gameViewModel.endGame()
                                        navController.navigate("statistics") {
                                            popUpTo("game") { inclusive = true }
                                            popUpTo("settings") { inclusive = true }
                                        }
                                    } else {
                                        gameViewModel.generateNewTask()
                                    }
                                }
                            },
                            isToggled = gameState.input.isEmpty(),
                            size = roundButtonSize,
                            iconId1 = R.drawable.round_keyboard_double_arrow_right_24,
                            iconId2 = R.drawable.round_keyboard_tab_24,
                            iconSize = iconSizeDoubleArrow
                        )
                    }
                }
            }
        }
    )

    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var showDialog by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showDialog = true
            }
        }
        backPressedDispatcher?.addCallback(callback)

        onDispose {
            callback.remove()
        }
    }

    if (showDialog) {
        QuitGamePopUp(
            onDismissRequest = {},
            onConfirm = {
                showDialog = false
                gameViewModel.endGame()
                navController.navigate("settings") {
                    popUpTo("game") { inclusive = true }
                    popUpTo("settings") { inclusive = true }
                }
            },
            onCancel = { showDialog = false },
            titleSize = titleFontSize,
            descriptionSize = textLabelFontSize
        )
    }
}
