package com.ag.kopfrechner.ui.component.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun SettingsOperatorColumn(
    operatorId: Int,
    operatorEnabled: Boolean,
    difficultyRange: Pair<Float, Float>,
    operatorSize: TextUnit,
    fontSize: TextUnit,
    operatorColor: Color,
) {

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var range = "-"
        if (operatorEnabled) {
            range = if (difficultyRange.first == difficultyRange.second) {
                difficultyRange.first.toInt().toString()
            } else {
                String.format(
                    "%s-%s",
                    difficultyRange.first.toInt(),
                    difficultyRange.second.toInt()
                )
            }
        }
        Text(
            text = stringResource(operatorId),
            fontSize = operatorSize,
            color = operatorColor,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = range,
            fontSize = fontSize,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}