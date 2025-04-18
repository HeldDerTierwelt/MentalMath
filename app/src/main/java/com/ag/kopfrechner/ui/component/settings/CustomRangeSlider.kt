package com.ag.kopfrechner.ui.component.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomRangeSlider(
    valueRange: ClosedFloatingPointRange<Float>,
    value: ClosedFloatingPointRange<Float>,
    activeTrackColor: Color,
    isEnabled: Boolean,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    size: Dp
) {
    val startInteractionSource = remember { MutableInteractionSource() }
    val endInteractionSource = remember { MutableInteractionSource() }
    val isDraggingStart by startInteractionSource.collectIsDraggedAsState()
    val isDraggingEnd by endInteractionSource.collectIsDraggedAsState()
    var sliderWidth by remember { mutableIntStateOf(0) }
    var sliderHeight by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                sliderWidth = it.size.width
                sliderHeight = it.size.height
            },
        contentAlignment = Alignment.Center
    ) {

        fun thumbOffset(thumbValue: Float): Int {
            val positionFraction =
                (thumbValue - valueRange.start) / (valueRange.endInclusive - valueRange.start)
            return ((sliderWidth) * positionFraction - sliderWidth / 2).toInt()
        }

        Text(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = thumbOffset(value.start),
                        y = (-size.roundToPx() / 1.25f).toInt()
                    )
                }
                .alpha(if (isDraggingStart) 1f else 0f)
                .background(
                    activeTrackColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 6.dp),
            text = String.format(Locale.GERMAN, "%.0f", value.start),
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 12.sp
        )

        Text(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = thumbOffset(value.endInclusive),
                        y = (-size.roundToPx() / 1.25f).toInt()
                    )
                }
                .alpha(if (isDraggingEnd) 1f else 0f)
                .background(
                    activeTrackColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 6.dp),
            text = String.format(Locale.GERMAN, "%.0f", value.endInclusive),
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 16.sp
        )

        val sliderColors: SliderColors
        if (isEnabled) {
            sliderColors = SliderDefaults.colors(
                thumbColor = activeTrackColor,
                activeTrackColor = activeTrackColor,
                inactiveTrackColor = MaterialTheme.colorScheme.surface,
                activeTickColor = MaterialTheme.colorScheme.background,
                inactiveTickColor = activeTrackColor
            )
        } else {
            sliderColors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.onSurface,
                activeTrackColor = MaterialTheme.colorScheme.onSurface,
                inactiveTrackColor = MaterialTheme.colorScheme.surface,
                activeTickColor = MaterialTheme.colorScheme.background,
                inactiveTickColor = MaterialTheme.colorScheme.onSurface
            )
        }

        RangeSlider(
            value = value,
            valueRange = valueRange,
            steps = valueRange.endInclusive.toInt() - valueRange.start.toInt() - 1,
            modifier = Modifier
                .fillMaxWidth()
                .height(size),
            colors = sliderColors,
            enabled = isEnabled,
            startInteractionSource = startInteractionSource,
            endInteractionSource = endInteractionSource,
            onValueChange = onValueChange
        )
    }
}