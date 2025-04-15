package com.ag.kopfrechner.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.ag.kopfrechner.ui.theme.green
import com.ag.kopfrechner.viewmodel.SettingsUiState

@Composable
fun StartButton(
    buttonTextId: Int,
    icon: ImageVector,
    state: SettingsUiState,
    onClick: () -> Unit,
    iconSize: Dp,
    size: Dp,
    fontSize: TextUnit
) {

    val isEnabled: Boolean = state.isPlusEnabled ||
            state.isMinusEnabled ||
            state.isMultiplyEnabled ||
            state.isDivideEnabled

    Button(
        enabled = isEnabled,
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(size)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Description",
                modifier = Modifier.size(iconSize),
                tint = if (isEnabled) green else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            )
            Text(
                text = stringResource(buttonTextId),
                fontSize = fontSize,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}