package com.universall.watertracker.core.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun SkeletonBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    color: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(color = color)
    )
}


@Composable
fun DottedVerticalSpacer(
    modifier: Modifier,
    dotSize: Dp = 1.dp,
    gapSize: Dp = 2.dp,
    color: Color = Color.LightGray
) {
    val density = LocalDensity.current
    val dotPx = with(density) { dotSize.toPx() }
    val gapPx = with(density) { gapSize.toPx() }

    Canvas(modifier = modifier.width(dotSize)) {
        val x = size.width / 2f

        drawLine(
            color = color,
            start = Offset(x, 0f),
            end = Offset(x, size.height),
            strokeWidth = dotPx,
            cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(dotPx, gapPx),
                phase = 0f
            )
        )
    }
}
