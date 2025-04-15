package com.ag.kopfrechner.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.ag.kopfrechner.ui.component.GameTopBar
import com.ag.kopfrechner.viewmodel.SettingsViewModel

@Composable
fun GameScreen(
    navController: NavController,
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
    val iconStart = (0.057f * screenHeight.value).dp
    val iconMode = (0.048f * screenHeight.value).dp
    val columnPadding = (0.028f * screenHeight.value).dp

    Scaffold(
        topBar = {
            GameTopBar(navController, viewModel, iconMode, textLabelFontSize)
            //SettingsTopBar(R.string.app_name)
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
                }
            }
        }
    )
}
