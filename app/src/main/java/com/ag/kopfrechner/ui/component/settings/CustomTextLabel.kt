package com.ag.kopfrechner.ui.component.settings

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit

@Composable
fun TextLabel(text: String, fontSize: TextUnit) {

    Text(
        text = text,
        fontSize = fontSize,
        color = MaterialTheme.colorScheme.onPrimary,
        lineHeight = fontSize
    )
}