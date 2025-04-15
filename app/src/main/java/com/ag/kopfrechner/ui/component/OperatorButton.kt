package com.ag.kopfrechner.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun OperatorButton(
    buttonTextId: Int,
    textColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    size: Dp,
    fontSize: TextUnit
) {

    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(size),
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(
            text = stringResource(buttonTextId),
            fontSize = fontSize,
            color = if (isSelected) textColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        )
    }
}