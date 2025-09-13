package com.ag.kopfrechner.ui.component.settings

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ag.kopfrechner.R
import com.ag.kopfrechner.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun SideSheet(
    isSheetOpen: Boolean,
    screenWidth: Dp,
    onDismissRequested: () -> Unit,
    screenHeight: Dp,
    appSymbolSize: Dp,
    settingsViewModel: SettingsViewModel
) {
    val overlayAlpha by animateFloatAsState(
        targetValue = if (isSheetOpen) 0.4f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "OverlayAlpha"
    )
    val density = LocalDensity.current
    val screenWidthPx = with(density) { screenWidth.toPx() }
    val offsetX = remember { Animatable(screenWidthPx) }
    val sheetWidthFactor = 0.6f
    val coroutineScope = rememberCoroutineScope()
    var isInteractionEnabled by remember { mutableStateOf(true) }

    val context = LocalContext.current

    val fontSize = (0.018f * screenHeight.value).sp
    val iconSize = (0.032f * screenHeight.value).dp
    val columnPadding = (0.028f * screenHeight.value).dp
    val themeMode = settingsViewModel.settingsState.value.themeMode

    // Handle side sheet open/close animation
    LaunchedEffect(isSheetOpen) {
        isInteractionEnabled = false
        val target = if (isSheetOpen) screenWidthPx * (1f - sheetWidthFactor) else screenWidthPx
        if (offsetX.value != target) {
            offsetX.animateTo(target)
        }
        isInteractionEnabled = true
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Dark overlay
        if (overlayAlpha > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = overlayAlpha))
                    .then(
                        if (isInteractionEnabled) {
                            Modifier.clickable(
                                indication = null,
                                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                            ) { onDismissRequested() }
                        } else {
                            Modifier
                        }
                    )
            )
        }

        // Side sheet
        Card(
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .fillMaxHeight()
                .width(screenWidth * sheetWidthFactor)
                .offset { IntOffset(offsetX.value.toInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            val newOffset = (offsetX.value + dragAmount).coerceIn(
                                screenWidthPx * (1f - sheetWidthFactor),
                                screenWidthPx
                            )
                            coroutineScope.launch {
                                offsetX.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            if (offsetX.value > screenWidthPx * (1f - sheetWidthFactor) + screenWidthPx * sheetWidthFactor * 0.2f) {
                                onDismissRequested()
                            } else {
                                coroutineScope.launch {
                                    offsetX.animateTo(screenWidthPx * (1f - sheetWidthFactor))
                                }
                            }
                        }
                    )
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {

                    // App Logo
                    Spacer(modifier = Modifier.size(16.dp))
                    Row(
                        modifier = Modifier
                            .padding(16.dp, 8.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mental_math),
                            contentDescription = "App Logo",
                            modifier = Modifier.fillMaxWidth(0.5f)
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))

                    // Dark Mode Toggle
                    DarkModeToggleRow(
                        fontSize = fontSize,
                        iconSize = iconSize,
                        currentMode = themeMode,
                        onModeChange = { settingsViewModel.setThemeMode(it) }
                    )

                    // Source Code Link
                    SideSheetLinkRow(
                        iconResId = R.drawable.code_24dp,
                        contentDescription = "Settings Icon",
                        text = "Source Code",
                        fontSize = fontSize,
                        iconSize = iconSize,
                        url = "https://www.example.com",
                        context = context
                    )

                    // License Link
                    SideSheetLinkRow(
                        iconResId = R.drawable.contract_24dp,
                        contentDescription = "License Icon",
                        text = "License",
                        fontSize = fontSize,
                        iconSize = iconSize,
                        url = "https://www.example.com",
                        context = context
                    )

                    // Privacy Policy Link
                    SideSheetLinkRow(
                        iconResId = R.drawable.domino_mask_24dp,
                        contentDescription = "Privacy Policy Icon",
                        text = "Privacy Policy",
                        fontSize = fontSize,
                        iconSize = iconSize,
                        url = "https://www.example.com",
                        context = context
                    )

                    // Feedback Link
                    SideSheetLinkRow(
                        iconResId = R.drawable.feedback_24dp,
                        contentDescription = "Feedback Icon",
                        text = "Feed\u200Bback",
                        fontSize = fontSize,
                        iconSize = iconSize,
                        url = "https://www.example.com",
                        context = context
                    )

                    // Donation Link
                    SideSheetLinkRow(
                        iconResId = R.drawable.volunteer_activism_24dp,
                        contentDescription = "Donation Icon",
                        text = "Donate",
                        fontSize = fontSize,
                        iconSize = iconSize,
                        url = "https://www.example.com",
                        context = context
                    )

                    // Recommendation Link
                    SideSheetLinkRow(
                        iconResId = R.drawable.thumb_up_24dp,
                        contentDescription = "Recommend",
                        text = "Recom\u200Bmend",
                        fontSize = fontSize,
                        iconSize = iconSize,
                        url = "https://www.example.com",
                        context = context
                    )
                }
            }
        }
    }
}