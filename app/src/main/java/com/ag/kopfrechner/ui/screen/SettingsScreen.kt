package com.ag.kopfrechner.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ag.kopfrechner.R
import com.ag.kopfrechner.ui.component.settings.CustomRangeSlider
import com.ag.kopfrechner.ui.component.settings.CustomSlider
import com.ag.kopfrechner.ui.component.settings.ModeButton
import com.ag.kopfrechner.ui.component.settings.OperatorButton
import com.ag.kopfrechner.ui.component.settings.SettingsTopBar
import com.ag.kopfrechner.ui.component.settings.SideSheet
import com.ag.kopfrechner.ui.component.settings.StartButton
import com.ag.kopfrechner.ui.component.settings.TextLabel
import com.ag.kopfrechner.ui.theme.blue
import com.ag.kopfrechner.ui.theme.green
import com.ag.kopfrechner.ui.theme.red
import com.ag.kopfrechner.ui.theme.softBlue
import com.ag.kopfrechner.ui.theme.softGreen
import com.ag.kopfrechner.ui.theme.softRed
import com.ag.kopfrechner.ui.theme.softYellow
import com.ag.kopfrechner.ui.theme.yellow
import com.ag.kopfrechner.viewmodel.GameViewModel
import com.ag.kopfrechner.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    gameViewModel: GameViewModel,
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val settingsState = settingsViewModel.settingsState.value
    val valueRangeOperators = 1f..9f
    var isSheetOpen by remember { mutableStateOf(false) }

    // Calculate sizes based on screen height
    val containerSizePx = LocalWindowInfo.current.containerSize // IntSize in px
    val density = LocalDensity.current
    val screenHeight = with(density) { containerSizePx.height.toDp() }
    val screenWidth = with(density) { containerSizePx.width.toDp() }

    val roundButtonSize = (0.09f * screenHeight.value).dp
    val sliderSize = (0.05f * screenHeight.value).dp
    val operatorButtonFontSize = (0.057f * screenHeight.value).sp
    val startButtonFontSize = (0.038f * screenHeight.value).sp
    val titleFontSize = (0.031f * screenHeight.value).sp
    val textLabelFontSize = (0.024f * screenHeight.value).sp
    val startIconSize = (0.057f * screenHeight.value).dp
    val modeIconSize = (0.048f * screenHeight.value).dp
    val columnPadding = (0.028f * screenHeight.value).dp
    val infoIconSize = (0.038f * screenHeight.value).dp

    Scaffold(
        topBar = {
            SettingsTopBar(
                R.string.app_name,
                titleFontSize,
                onInfoClick = { isSheetOpen = !isSheetOpen },
                infoIconSize
            )
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
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    TextLabel(
                        text = stringResource(R.string.mode_instruction),
                        fontSize = textLabelFontSize
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ModeButton(
                            iconId1 = R.drawable.tag,
                            iconId2 = R.drawable.round_hourglass_bottom_24,
                            isToggled = settingsState.isModeEnabled,
                            onClick = { settingsViewModel.toggleMode() },
                            iconSize = modeIconSize,
                            size = roundButtonSize
                        )
                        Spacer(Modifier.padding(8.dp))
                        CustomSlider(
                            value = settingsState.limit,
                            onValueChange = { settingsViewModel.updateLimit(it) },
                            valueRange = 1f..5f,
                            isToggled = settingsState.isModeEnabled,
                            size = sliderSize,
                            activeTrackColor = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    TextLabel(
                        text = stringResource(R.string.operator_instruction),
                        fontSize = textLabelFontSize
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OperatorButton(
                            buttonTextId = R.string.add,
                            textColor = green,
                            isSelected = settingsState.isPlusEnabled,
                            onClick = { settingsViewModel.togglePlus() },
                            size = roundButtonSize,
                            fontSize = operatorButtonFontSize,
                        )
                        Spacer(Modifier.padding(8.dp))
                        CustomRangeSlider(
                            valueRange = valueRangeOperators,
                            value = settingsState.plusRange,
                            activeTrackColor = if (isSystemInDarkTheme()) softGreen else green,
                            isEnabled = settingsState.isPlusEnabled,
                            onValueChange = { settingsViewModel.updatePlusRange(it) },
                            size = sliderSize
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OperatorButton(
                            buttonTextId = R.string.subtract,
                            textColor = red,
                            isSelected = settingsState.isMinusEnabled,
                            onClick = { settingsViewModel.toggleMinus() },
                            size = roundButtonSize,
                            fontSize = operatorButtonFontSize
                        )
                        Spacer(Modifier.padding(8.dp))
                        CustomRangeSlider(
                            valueRange = valueRangeOperators,
                            value = settingsState.minusRange,
                            activeTrackColor = if (isSystemInDarkTheme()) softRed else red,
                            isEnabled = settingsState.isMinusEnabled,
                            onValueChange = { settingsViewModel.updateMinusRange(it) },
                            size = sliderSize
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OperatorButton(
                            buttonTextId = R.string.multiply,
                            textColor = yellow,
                            isSelected = settingsState.isMultiplyEnabled,
                            onClick = { settingsViewModel.toggleMultiply() },
                            size = roundButtonSize,
                            fontSize = operatorButtonFontSize
                        )
                        Spacer(Modifier.padding(8.dp))
                        CustomRangeSlider(
                            valueRange = valueRangeOperators,
                            value = settingsState.multiplyRange,
                            activeTrackColor = if (isSystemInDarkTheme()) softYellow else yellow,
                            isEnabled = settingsState.isMultiplyEnabled,
                            onValueChange = { settingsViewModel.updateMultiplyRange(it) },
                            size = sliderSize
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OperatorButton(
                            buttonTextId = R.string.divide,
                            textColor = blue,
                            isSelected = settingsState.isDivideEnabled,
                            onClick = { settingsViewModel.toggleDivide() },
                            size = roundButtonSize,
                            fontSize = operatorButtonFontSize
                        )
                        Spacer(Modifier.padding(8.dp))
                        CustomRangeSlider(
                            valueRange = valueRangeOperators,
                            value = settingsState.divideRange,
                            activeTrackColor = if (isSystemInDarkTheme()) softBlue else blue,
                            isEnabled = settingsState.isDivideEnabled,
                            onValueChange = { settingsViewModel.updateDivideRange(it) },
                            size = sliderSize
                        )
                    }
                    StartButton(
                        buttonTextId = R.string.start,
                        icon = Icons.Rounded.PlayArrow,
                        settingsViewModel = settingsViewModel,
                        onClick = {
                            gameViewModel.resetGame()
                            gameViewModel.setEnabledOperators(settingsViewModel)
                            gameViewModel.startGame()
                            navController.navigate("game") {
                                popUpTo("settings") { inclusive = true }
                            }
                        },
                        size = roundButtonSize,
                        fontSize = startButtonFontSize,
                        iconSize = startIconSize
                    )
                }
                SideSheet(
                    isSheetOpen = isSheetOpen,
                    screenWidth = screenWidth,
                    sheetModifier = Modifier.pointerInput(Unit) {
                        detectHorizontalDragGestures { change, dragAmount ->
                            if (dragAmount > 20) {
                                isSheetOpen = false
                            }
                        }
                    },
                    boxModifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { isSheetOpen=false }

                )
            }
        }
    )

    BackHandler {
        (context as? android.app.Activity)?.moveTaskToBack(true)
    }
}
