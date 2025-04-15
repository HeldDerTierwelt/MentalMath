package com.ag.kopfrechner.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.ag.kopfrechner.viewmodel.SettingsViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(
    navController: NavController,
    viewModel: SettingsViewModel,
    iconSize: Dp,
    fontSize: TextUnit
) {

    TopAppBar(
        title = {
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            IconButton(
                onClick = { navController.navigate("settings") }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier.size(iconSize),
                    tint = red
                )
            }
        },
        actions = {
            val limit = viewModel.uiState.value.limit
            if (viewModel.uiState.value.isModeEnabled) {
                Text(
                    text = (limit * 10).roundToInt().toString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = fontSize,
                    modifier = Modifier.padding(end = 16.dp)
                )
            } else {
                CountdownTimer(
                    initialTimeInSeconds = (limit * 60).toInt(),
                    fontSize = fontSize,
                    navController = navController
                )
            }
        },
    )
}