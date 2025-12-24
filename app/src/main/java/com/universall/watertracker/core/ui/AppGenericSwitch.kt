package com.universall.watertracker.core.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
private fun SwitchThumb(
    checked: Boolean,
    maxWidth: Dp,
    maxHeight: Dp,
    thumbColor: Color,
    thumbPadding: Dp
) {
    val thumbSize = maxHeight - thumbPadding * 2

    val offsetX by animateDpAsState(
        targetValue = if (checked) maxWidth - thumbSize - thumbPadding else thumbPadding,
        label = "ThumbOffset"
    )

    Box(
        modifier = Modifier
            .offset(x = offsetX, y = thumbPadding)
            .size(thumbSize)
            .clip(CircleShape)
            .background(thumbColor)
    )
}

@Composable
fun AppGenericSwitch(
    modifier: Modifier = Modifier,
    checkedColor: Color,
    uncheckedColor: Color,
    thumbColor: Color,
    thumbPadding: Dp = 3.dp,
    checked: Boolean,
    onCheckedChange: (checked: Boolean) -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(1.8f)
            .clip(RoundedCornerShape(50))
            .background(if (checked) checkedColor else uncheckedColor)
            .clickable { onCheckedChange(!checked) }
    ) {
        SwitchThumb(
            checked = checked,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            thumbColor = thumbColor,
            thumbPadding = thumbPadding
        )
    }
}

@Composable
fun AppGenericMaterialSwitch(
    modifier: Modifier = Modifier,
    checkedColor: Color? = null,
    uncheckedColor: Color? = null,
    thumbColor: Color? = null,
    thumbPadding: Dp = 3.dp,
    checked: Boolean,
    onCheckedChange: (checked: Boolean) -> Unit,
) {
    val colors = MaterialTheme.colorScheme

    AppGenericSwitch(
        modifier = modifier,
        checkedColor = checkedColor ?: colors.primary,
        uncheckedColor = uncheckedColor ?: colors.tertiary,
        thumbColor = thumbColor ?: colors.surface,
        thumbPadding = thumbPadding,
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}
