package com.ag.kopfrechner.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun CustomSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    isToggled: Boolean,
    size: Dp,
    activeTrackColor: Color,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isDragging by interactionSource.collectIsDraggedAsState()
    var sliderWidth by remember { mutableIntStateOf(0) }
    var sliderHeight by remember { mutableIntStateOf(0) }
    val lastTickSpace = (0.084f * LocalConfiguration.current.screenWidthDp.dp.value).dp.value

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                sliderWidth = it.size.width
                sliderHeight = it.size.height
            }, contentAlignment = Alignment.Center
    ) {

        val positionFraction =
            (value - valueRange.start) / (valueRange.endInclusive - valueRange.start)
        val offsetForLastTick = if (value == valueRange.endInclusive) lastTickSpace else 0.dp.value
        val offsetX =
            ((sliderWidth) * positionFraction - sliderWidth / 2 - offsetForLastTick).toInt()
        Text(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = offsetX, y = (-size.roundToPx() / 1.25f).toInt()
                    )
                }
                .alpha(if (isDragging) 1f else 0f)
                .background(
                    activeTrackColor, shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 6.dp),
            text = String.format(
                Locale.GERMAN,
                if (isToggled) "${(value * 10).roundToInt()} ex" else "${value.toInt()} min",
                value
            ),
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 16.sp
        )

        val sliderColors = SliderDefaults.colors(
            thumbColor = activeTrackColor,
            activeTrackColor = activeTrackColor,
            inactiveTrackColor = MaterialTheme.colorScheme.surface,
            activeTickColor = MaterialTheme.colorScheme.background,
            inactiveTickColor = activeTrackColor
        )

        Slider(
            onValueChange = onValueChange,
            value = value,
            valueRange = valueRange,
            steps = valueRange.endInclusive.toInt() - valueRange.start.toInt() - 1,
            modifier = Modifier
                .fillMaxWidth()
                .height(size),
            colors = sliderColors,
            interactionSource = interactionSource
        )
    }
}
