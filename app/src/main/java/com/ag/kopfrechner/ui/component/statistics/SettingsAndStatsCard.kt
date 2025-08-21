package com.ag.kopfrechner.ui.component.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.ag.kopfrechner.R
import com.ag.kopfrechner.ui.theme.blue
import com.ag.kopfrechner.ui.theme.green
import com.ag.kopfrechner.ui.theme.red
import com.ag.kopfrechner.ui.theme.yellow
import com.ag.kopfrechner.viewmodel.GameViewModel
import com.ag.kopfrechner.viewmodel.SettingsViewModel
import java.util.Locale
import kotlin.math.pow

@Composable
fun SettingsAndStatsCard(
    gameViewModel: GameViewModel,
    settingsViewModel: SettingsViewModel,
    operatorSize: TextUnit,
    fontSize: TextUnit,
    iconSize: Dp
) {
    val gameState = gameViewModel.gamesState.value
    val totalTime = gameState.endTimeStamp - gameState.startTimeStamp
    val percentage = gameState.correctAnswers.toFloat() /
            gameState.totalAnswers.toFloat() * 100f
    val percentageString = if (percentage.isNaN()) "-" else String.format(
        Locale.getDefault(),
        "%.1f",
        percentage
    )
    val correctPerMinute = String.format(
        Locale.getDefault(),
        "%.1f",
        gameState.correctAnswers.toFloat() /
                (gameState.activeTime.toFloat() / 60f)
    )
    val activeScoreValue = gameState.correctAnswers.toFloat().pow(2f) /
            ((gameState.activeTime.toFloat() / 60f) * maxOf(1f,gameState.totalAnswers.toFloat()))
    val activeScore = String.format(
        Locale.getDefault(), "%s:  %.2f", stringResource(R.string.active_score),
        activeScoreValue
    )
    val totalScoreValue = gameState.correctAnswers.toFloat().pow(2f) /
            ((totalTime.toFloat() / 60f) * maxOf(1f,gameState.totalAnswers.toFloat()))
    val totalScore = String.format(
        Locale.getDefault(), "%s:  %.2f", stringResource(R.string.total_score),
        totalScoreValue
    )
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
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SettingsModeColumn(
                    modeEnabled = settingsViewModel.settingsState.value.isModeEnabled,
                    fontSize = fontSize,
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
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                StatsProgressColumn(
                    modeEnabled = settingsViewModel.settingsState.value.isModeEnabled,
                    fontSize = fontSize,
                    iconSize = iconSize,
                    totalAnswers = gameState.totalAnswers,
                    activeTime = gameState.activeTime
                )
                StatsColumn(
                    fontSize = fontSize,
                    statsText = gameState.correctAnswers.toString(),
                    iconId = R.drawable.round_check_24,
                    iconSize = iconSize,
                    iconColor = green
                )

                StatsColumn(
                    fontSize = fontSize,
                    statsText = percentageString,
                    iconId = R.drawable.round_percent_24,
                    iconSize = iconSize,
                    iconColor = green
                )

                StatsCorrectPerMinuteColumn(
                    iconId = R.drawable.round_check_24,
                    iconSize = iconSize,
                    iconColor = green,
                    iconText = "min",
                    iconFontSize = fontSize / 2,
                    fontSize = fontSize,
                    text = correctPerMinute
                )

                StatsColumn(
                    fontSize = fontSize,
                    statsText = formatTime(totalTime),
                    iconId = R.drawable.round_access_time_24,
                    iconSize = iconSize,
                    iconColor = MaterialTheme.colorScheme.onPrimary
                )
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
            Text(
                text = activeScore,
                fontSize = fontSize,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
                Icon(
                    painter = painterResource(R.drawable.round_star_24),
                    contentDescription = "activeSoreIcon",
                    modifier = Modifier.size(iconSize),
                    tint = yellow
                )}
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
            Text(
                text = totalScore,
                fontSize = fontSize,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
                Icon(
                    painter = painterResource(R.drawable.round_star_24),
                    contentDescription = "totalScoreIcon",
                    modifier = Modifier.size(iconSize),
                    tint = green
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
