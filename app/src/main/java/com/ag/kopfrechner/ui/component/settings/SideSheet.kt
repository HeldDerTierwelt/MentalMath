package com.ag.kopfrechner.ui.component.settings

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SideSheet(
    isSheetOpen: Boolean,
    screenWidth: Dp,
    onDismissRequested: () -> Unit
) {
    val overlayAlpha by animateFloatAsState(
        targetValue = if (isSheetOpen) 0.4f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "OverlayAlpha"
    )
    val density = LocalDensity.current
    val screenWidthPx = with(density) { screenWidth.toPx() }
    val offsetX = remember { Animatable(screenWidthPx) }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(isSheetOpen) {
        val target = if (isSheetOpen) screenWidthPx * 0.25f else screenWidthPx
        if (offsetX.value != target) {
            offsetX.animateTo(target)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (overlayAlpha > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = overlayAlpha))
                    .clickable(
                        indication = null,
                        interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                    ) { onDismissRequested() }
            )
        }

        Card(
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .fillMaxHeight()
                .width(screenWidth * 0.75f)
                .offset{IntOffset(offsetX.value.toInt(), 0)}
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            val newOffset = (offsetX.value + dragAmount).coerceIn(screenWidthPx*0.25f, screenWidthPx)
                            coroutineScope.launch {
                            offsetX.snapTo(newOffset)}
                        },
                        onDragEnd = {
                            if (offsetX.value > screenWidthPx*0.25f + screenWidthPx*0.75f * 0.25f) {
                                onDismissRequested()
                            } else {
                                coroutineScope.launch {
                                    offsetX.animateTo(screenWidthPx*0.25f)
                                }
                            }
                        }
                    )
                }
            ,
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