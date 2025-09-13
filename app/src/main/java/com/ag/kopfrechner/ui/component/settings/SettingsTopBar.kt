package com.ag.kopfrechner.ui.component.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.ag.kopfrechner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar(
    titleId: Int,
    titleFontSize: TextUnit,
    onInfoClick: () -> Unit,
    iconSize: Dp,
    appSymbolSize: Dp,
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mental_math),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(appSymbolSize)
                        .padding(bottom = 1.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(text = stringResource(id = titleId), fontSize = titleFontSize)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        actions = {
            IconButton(
                onClick = onInfoClick,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Icon(
                    painterResource(R.drawable.info_24dp),
                    contentDescription = "share",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(iconSize),
                )
            }
        }
    )
}