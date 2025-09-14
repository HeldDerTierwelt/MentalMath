/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.ui.component.game


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import com.helddertierwelt.mentalmath.ui.theme.green
import com.helddertierwelt.mentalmath.ui.theme.red

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