/*
Mental Math - Android app for practicing mental arithmetic
Copyright (C) 2025 HeldDerTierwelt

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see https://www.gnu.org/licenses/gpl-3.0.md.
*/

package com.helddertierwelt.mentalmath.presentation.component.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.helddertierwelt.mentalmath.presentation.theme.green
import com.helddertierwelt.mentalmath.presentation.theme.red

@Composable
fun MathTaskDisplay(
    operand1: Int,
    operand2: Int,
    operator: Int,
    input: String,
    fontSize: TextUnit,
    width: Dp,
    isCorrect: Boolean?
) {

    val operatorString = stringResource(operator)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "$operand1 $operatorString $operand2",
            fontSize = fontSize,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height((0.5 * fontSize.value).dp))

        Box(
            modifier = Modifier.width(width + (1.2 * fontSize.value).dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "=",
                fontSize = fontSize,
            )
            Spacer(modifier = Modifier.width(8.dp))
            val color = MaterialTheme.colorScheme.onSurface
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(width)
                    .height((fontSize * 1.5f).value.dp)
                    .drawBehind {
                        drawLine(
                            color = color,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 2.dp.toPx()
                        )
                    },
                contentAlignment = Alignment.CenterStart
            ) {

                val textColor =
                    if (isCorrect == true) green else if (isCorrect == false) red else MaterialTheme.colorScheme.onPrimary
                Text(
                    text = input,
                    fontSize = fontSize,
                    modifier = Modifier.align(Alignment.Center),
                    color = textColor
                )
            }
        }
    }
}
