package com.ag.kopfrechner.ui.component.game

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun NumberButton(
    number: Int,
    onClick: () -> Unit,
    size: Dp,
    fontSize: TextUnit,
) {

    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(size),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = number.toString(),
            fontSize = fontSize,
        )
    }
}