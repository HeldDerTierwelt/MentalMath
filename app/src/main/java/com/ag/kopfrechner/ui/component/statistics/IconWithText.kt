package com.ag.kopfrechner.ui.component.statistics

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun IconWithText(
    iconId: Int,
    iconSize: Dp,
    iconColor: Color,
    iconText: String,
    fontSize: TextUnit
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = "modeIcon",
            modifier = Modifier.size(iconSize),
            tint = iconColor
        )
        Text(
            text = iconText,
            fontSize = fontSize,
            modifier = Modifier
                .padding(bottom = 0.dp)
                .padding(end = 4.dp)
        )
    }
}