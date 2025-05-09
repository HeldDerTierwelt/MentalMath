package com.ag.kopfrechner.ui.component.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.ag.kopfrechner.R
import com.ag.kopfrechner.ui.theme.blue
import com.ag.kopfrechner.ui.theme.green
import com.ag.kopfrechner.ui.theme.red
import com.ag.kopfrechner.ui.theme.yellow
import com.ag.kopfrechner.viewmodel.SettingsViewModel

@Composable
fun SettingsCard(
    settingsViewModel: SettingsViewModel,
    operatorSize: TextUnit,
    fontSize: TextUnit,
    iconSize: Dp
) {
    Card(
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            SettingsModeColumn(
                modeEnabled = settingsViewModel.settingsState.value.isModeEnabled,
                fontSize= fontSize,
                iconSize = iconSize,
                limit = settingsViewModel.settingsState.value.limit.toInt()
            )
            SettingsOperatorColumn(
                operatorSize = operatorSize,
                fontSize = fontSize,
                operatorEnabled = settingsViewModel.settingsState.value.isPlusEnabled,
                difficultyRange = settingsViewModel.settingsState.value.plusRange,
                operatorId = R.string.add,
                operatorColor = green
            )
            SettingsOperatorColumn(
                operatorSize = operatorSize,
                fontSize = fontSize,
                operatorEnabled = settingsViewModel.settingsState.value.isMinusEnabled,
                difficultyRange = settingsViewModel.settingsState.value.minusRange,
                operatorId = R.string.subtract,
                operatorColor = red
            )
            SettingsOperatorColumn(
                operatorSize = operatorSize,
                fontSize = fontSize,
                operatorEnabled = settingsViewModel.settingsState.value.isMultiplyEnabled,
                difficultyRange = settingsViewModel.settingsState.value.multiplyRange,
                operatorId = R.string.multiply,
                operatorColor = yellow
            )
            SettingsOperatorColumn(
                operatorSize = operatorSize,
                fontSize = fontSize,
                operatorEnabled = settingsViewModel.settingsState.value.isDivideEnabled,
                difficultyRange = settingsViewModel.settingsState.value.divideRange,
                operatorId = R.string.divide,
                operatorColor = blue
            )
        }
    }
}