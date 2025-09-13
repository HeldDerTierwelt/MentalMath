package com.ag.kopfrechner.ui.component.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SideSheet(
    isSheetOpen: Boolean,
    screenWidth: Dp,
    sheetModifier: Modifier = Modifier,
    boxModifier: Modifier = Modifier,
) {
    val overlayAlpha by animateFloatAsState(
        targetValue = if (isSheetOpen) 0.4f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "OverlayAlpha"
    )
    Box(modifier = Modifier.fillMaxSize()) {
        if (overlayAlpha > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = overlayAlpha))
                    .then(boxModifier)
            )
        }

        AnimatedVisibility(
            visible = isSheetOpen,
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(200)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(200)
            ),
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Card(
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(screenWidth * 0.75f)
                    .then(sheetModifier),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Settings", style = MaterialTheme.typography.titleMedium)
                    // Weitere Inhalte
                }
            }
        }
    }
}