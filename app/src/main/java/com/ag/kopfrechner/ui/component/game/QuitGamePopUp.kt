package com.ag.kopfrechner.ui.component.game


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import com.ag.kopfrechner.ui.theme.green
import com.ag.kopfrechner.ui.theme.red
import com.ag.kopfrechner.ui.theme.softGreen
import com.ag.kopfrechner.ui.theme.softRed

@Composable
fun QuitGamePopUp(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    titleSize: TextUnit,
    descriptionSize: TextUnit,
) {
    val quitColor = if (isSystemInDarkTheme()) softRed else red
    val continueColor = if (isSystemInDarkTheme()) softGreen else green

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