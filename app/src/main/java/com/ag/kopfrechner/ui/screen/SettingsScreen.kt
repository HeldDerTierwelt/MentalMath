package com.ag.kopfrechner.ui.screen

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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ag.kopfrechner.R
import com.ag.kopfrechner.ui.component.CustomRangeSlider
import com.ag.kopfrechner.ui.component.CustomSlider
import com.ag.kopfrechner.ui.component.ModeButton
import com.ag.kopfrechner.ui.component.OperatorButton
import com.ag.kopfrechner.ui.component.StartButton
import com.ag.kopfrechner.ui.component.TextLabel
import com.ag.kopfrechner.ui.theme.blue
import com.ag.kopfrechner.ui.theme.green
import com.ag.kopfrechner.ui.theme.red
import com.ag.kopfrechner.ui.theme.softBlue
import com.ag.kopfrechner.ui.theme.softGreen
import com.ag.kopfrechner.ui.theme.softRed
import com.ag.kopfrechner.ui.theme.softYellow
import com.ag.kopfrechner.ui.theme.yellow
import com.ag.kopfrechner.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.uiState.value
    val valueRangeOperators = 1f..9f

    // Calculate sizes based on screen height
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val roundButtonSize = (0.085f * screenHeight.value).dp
    val sliderSize = (0.05f * screenHeight.value).dp
    val operatorButtonFontSize = (0.057f * screenHeight.value).sp
    val startButtonFontSize = (0.038f * screenHeight.value).sp
    val textLabelFontSize = (0.024f * screenHeight.value).sp
    val iconSize = (0.057f * screenHeight.value).dp
    val columnPadding = (0.028f * screenHeight.value).dp

    Surface(
        modifier = Modifier.fillMaxSize(),
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
                // text= configuration.screenWidthDp.dp.value.toString(),
                text = "Choose mode and limit!",
                fontSize = textLabelFontSize
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ModeButton(
                    iconId1 = R.drawable.tag,
                    iconId2 = R.drawable.round_hourglass_bottom_24,
                    isToggled = state.isModeEnabled,
                    onClick = { viewModel.toggleMode() },
                    iconSize = iconSize,
                    size = roundButtonSize
                )
                Spacer(Modifier.padding(8.dp))
                CustomSlider(
                    value = state.limit,
                    onValueChange = { viewModel.updateLimit(it) },
                    valueRange = 1f..10f,
                    isToggled = state.isModeEnabled,
                    size = sliderSize,
                    activeTrackColor = MaterialTheme.colorScheme.onSurface
                )
            }
            TextLabel(
                text = "Choose operators and difficulty!",
                fontSize = textLabelFontSize
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OperatorButton(
                    buttonTextId = R.string.add,
                    textColor = green,
                    isSelected = state.isPlusEnabled,
                    onClick = { viewModel.togglePlus() },
                    size = roundButtonSize,
                    fontSize = operatorButtonFontSize,
                )
                Spacer(Modifier.padding(8.dp))
                CustomRangeSlider(
                    valueRange = valueRangeOperators,
                    value = state.plusRange,
                    activeTrackColor = if (isSystemInDarkTheme()) softGreen else green,
                    isEnabled = state.isPlusEnabled,
                    onValueChange = { viewModel.updatePlusRange(it) },
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
                    isSelected = state.isMinusEnabled,
                    onClick = { viewModel.toggleMinus() },
                    size = roundButtonSize,
                    fontSize = operatorButtonFontSize
                )
                Spacer(Modifier.padding(8.dp))
                CustomRangeSlider(
                    valueRange = valueRangeOperators,
                    value = state.minusRange,
                    activeTrackColor = if (isSystemInDarkTheme()) softRed else red,
                    isEnabled = state.isMinusEnabled,
                    onValueChange = { viewModel.updateMinusRange(it) },
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
                    isSelected = state.isMultiplyEnabled,
                    onClick = { viewModel.toggleMultiply() },
                    size = roundButtonSize,
                    fontSize = operatorButtonFontSize
                )
                Spacer(Modifier.padding(8.dp))
                CustomRangeSlider(
                    valueRange = valueRangeOperators,
                    value = state.multiplyRange,
                    activeTrackColor = if (isSystemInDarkTheme()) softYellow else yellow,
                    isEnabled = state.isMultiplyEnabled,
                    onValueChange = { viewModel.updateMultiplyRange(it) },
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
                    isSelected = state.isDivideEnabled,
                    onClick = { viewModel.toggleDivide() },
                    size = roundButtonSize,
                    fontSize = operatorButtonFontSize
                )
                Spacer(Modifier.padding(8.dp))
                CustomRangeSlider(
                    valueRange = valueRangeOperators,
                    value = state.divideRange,
                    activeTrackColor = if (isSystemInDarkTheme()) softBlue else blue,
                    isEnabled = state.isDivideEnabled,
                    onValueChange = { viewModel.updateDivideRange(it) },
                    size = sliderSize
                )
            }
            StartButton(
                buttonTextId = R.string.start,
                icon = Icons.Rounded.PlayArrow,
                state = state,
                onClick = {},
                size = roundButtonSize,
                fontSize = startButtonFontSize,
                iconSize = iconSize
            )
        }
    }
}