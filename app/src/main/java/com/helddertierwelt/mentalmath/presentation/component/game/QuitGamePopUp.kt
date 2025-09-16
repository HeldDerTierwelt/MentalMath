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


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import com.helddertierwelt.mentalmath.presentation.theme.green
import com.helddertierwelt.mentalmath.presentation.theme.red

@Composable
fun QuitGamePopUp(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    titleSize: TextUnit,
    descriptionSize: TextUnit,
) {
    val quitColor = red
    val continueColor = green

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Quit Game?", fontSize = titleSize)
        },
        text = {
            Text(text = "Your current progress will be lost.", fontSize = descriptionSize)
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = quitColor,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                onClick = { onConfirm() }
            ) {
                Text("Quit", fontSize = descriptionSize)
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = continueColor,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                onClick = { onCancel() }
            ) {
                Text("Continue", fontSize = descriptionSize)
            }
        }
    )
}