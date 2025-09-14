/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.ui.component.statistics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.helddertierwelt.mentalmath.R
import com.helddertierwelt.mentalmath.ui.theme.green
import com.helddertierwelt.mentalmath.ui.theme.red
import com.helddertierwelt.mentalmath.ui.theme.yellow
import com.helddertierwelt.mentalmath.viewmodel.GameViewModel
import java.util.Locale

@Composable
fun TasksCard(
    gameViewModel: GameViewModel,
    resultFontSize: androidx.compose.ui.unit.TextUnit,
    iconSize: androidx.compose.ui.unit.Dp
) {
    var isExpanded by remember { mutableStateOf(true) }
    Card(
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),

                text = stringResource(R.string.exercises),
                fontSize = resultFontSize
            )
            Icon(
                painter = painterResource(if (isExpanded) R.drawable.round_expand_less_24 else R.drawable.round_expand_more_24),
                contentDescription = "Expand",
                modifier = Modifier
                    .size(iconSize)
                    .clickable { isExpanded = !isExpanded }
            )
        }
        if (isExpanded) {
            gameViewModel.gamesState.value.tasks.forEach { task ->
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val exercise = String.format(
                        Locale.getDefault(),
                        "%d %s %d = %d",
                        task.operand1,
                        stringResource(task.operator),
                        task.operand2,
                        task.correctResult
                    )
                    Text(
                        text = exercise,
                        fontSize = resultFontSize,
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

                        false -> {
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = task.userInput,
                                color = red,
                                fontSize = resultFontSize,
                                modifier = Modifier.align(Alignment.CenterVertically)
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
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}