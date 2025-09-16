/*
Mental Math - Android app for practicing mental arithmetic
Copyright (C) 2025 HeldDerTierwelt

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see https://www.gnu.org/licenses/gpl-3.0.md.
*/

package com.helddertierwelt.mentalmath.presentation.component.settings

import android.content.Intent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.core.net.toUri
import com.helddertierwelt.mentalmath.R
import com.helddertierwelt.mentalmath.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun SideSheet(
    isSheetOpen: Boolean,
    screenWidth: Dp,
    onDismissRequested: () -> Unit,
    screenHeight: Dp,
    settingsViewModel: SettingsViewModel
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val screenWidthPx = with(LocalDensity.current) { screenWidth.toPx() }
    val sheetWidthFactor = 0.6f
    val offsetX = remember { Animatable(screenWidthPx) }
    val minOffset = screenWidthPx * (1f - sheetWidthFactor)
    val maxOffset = screenWidthPx
    val overlayAlpha = 0.4f * (1 - (offsetX.value - minOffset) / (maxOffset - minOffset))

    val fontSize = (0.018f * screenHeight.value).sp
    val iconSize = (0.032f * screenHeight.value).dp
    var isInteractionEnabled by remember { mutableStateOf(true) }

    // Handle side sheet open/close animation
    LaunchedEffect(isSheetOpen) {
        isInteractionEnabled = false
        val target = if (isSheetOpen) screenWidthPx * (1f - sheetWidthFactor) else screenWidthPx
        if (offsetX.value != target) {
            offsetX.animateTo(
                targetValue = target,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
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
                modifier = Modifier
                    .fillMaxWidth()
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
                        currentMode = settingsViewModel.settingsState.value.themeMode,
                        onModeChange = { settingsViewModel.setThemeMode(it) }
                    )

                    // Source Code Link
                    SideSheetLinkRow(
                        iconResId = R.drawable.code_24dp,
                        contentDescription = "Source Code",
                        text = "Source Code",
                        fontSize = fontSize,
                        iconSize = iconSize,
                        intentProvider = {
                            Intent(
                                Intent.ACTION_VIEW,
                                "https://github.com/HeldDerTierwelt/MentalMath".toUri()
                            )
                        },
                        context = context
                    )

                    // License Link
                    SideSheetLinkRow(
                        iconResId = R.drawable.contract_24dp,
                        contentDescription = "License",
                        text = "License",
                        fontSize = fontSize,
                        iconSize = iconSize,
                        intentProvider = {
                            Intent(
                                Intent.ACTION_VIEW,
                                "https://github.com/HeldDerTierwelt/MentalMath/blob/main/LICENSE.md".toUri()
                            )
                        },
                        context = context
                    )

                    // Privacy Policy Link
                    SideSheetLinkRow(
                        iconResId = R.drawable.domino_mask_24dp,
                        contentDescription = "Privacy Policy",
                        text = "Privacy Policy",
                        fontSize = fontSize,
                        iconSize = iconSize,
                        intentProvider = {
                            Intent(
                                Intent.ACTION_VIEW,
                                "https://github.com/HeldDerTierwelt/MentalMath/blob/main/PRIVACY_POLICY.md".toUri()
                            )
                        },
                        context = context
                    )

                    // Feedback Link
                    SideSheetLinkRow(
                        iconResId = R.drawable.feedback_24dp,
                        contentDescription = "Feedback",
                        text = "Feed\u200Bback",
                        fontSize = fontSize,
                        iconSize = iconSize,
                        intentProvider = {
                            Intent(Intent.ACTION_SEND)
                                .apply {
                                    selector = Intent(Intent.ACTION_SENDTO, "mailto:".toUri())
                                    putExtra(
                                        Intent.EXTRA_EMAIL,
                                        arrayOf("mental_math_feedback@pm.me")
                                    )
                                    putExtra(Intent.EXTRA_SUBJECT, "Mental Math: Feedback")
                                }
                        },
                        context = context
                    )

                    // Donation Link
                    SideSheetLinkRow(
                        iconResId = R.drawable.volunteer_activism_24dp,
                        contentDescription = "Donate",
                        text = "Donate",
                        fontSize = fontSize,
                        iconSize = iconSize,
                        intentProvider = {
                            Intent(
                                Intent.ACTION_VIEW,
                                "https://ko-fi.com/mental_math".toUri()
                            )
                        },
                        context = context
                    )

                    // Recommendation Link
                    /*SideSheetLinkRow(
                        iconResId = R.drawable.thumb_up_24dp,
                        contentDescription = "Recommend",
                        text = "Recom\u200Bmend",
                        fontSize = fontSize,
                        iconSize = iconSize,
                        intentProvider = {
                            Intent(
                                Intent.ACTION_VIEW,
                                "https://www.example.com".toUri()
                            )
                        },
                        context = context
                    )*/
                }
            }
        }
    }
}