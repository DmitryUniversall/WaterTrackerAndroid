package com.universall.watertracker.main.features.stats.ui.stats_view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Fill
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.fill.Drop
import com.adamglin.phosphoricons.regular.Clock
import com.universall.watertracker.core.toHHMM
import com.universall.watertracker.core.ui.SkeletonBox
import com.universall.watertracker.main.features.stats.ui.stats_view.StatsViewModel


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

@Composable
fun RecordBlock(
    descriptionText: String,
    timeString: String,
    icon: ImageVector,
    iconColor: Color? = null
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = icon,
                contentDescription = null,
                tint = iconColor ?: colors.onSurface
            )
            Text(
                text = descriptionText,
                color = colors.onSurface,
                style = typography.bodyLarge
            )
        }

        Text(
            text = timeString,
            color = colors.onBackground,
            style = typography.bodyLarge
        )
    }
}

@Composable
fun DayRecordsSelection(
    viewModel: StatsViewModel
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colors.surface,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(shape = RoundedCornerShape(24.dp))
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Today's record",
                color = colors.onSurface,
                style = typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            RecordBlock(
                descriptionText = "Next reminder",
                timeString = "17:00 am",
                icon = PhosphorIcons.Regular.Clock
            )

            if (!uiState.isLoading) {
                uiState.selectedDayStats!!.waterIntakes.forEach { intake ->
                    DottedVerticalSpacer(
                        modifier = Modifier
                            .height(24.dp)
                            .padding(start = 15.dp)
                    )

                    RecordBlock(
                        descriptionText = "${intake.amount}${intake.waterMeasureUnit.titleShort}",
                        timeString = intake.dateTime.toLocalTime().toHHMM(),
                        icon = PhosphorIcons.Fill.Drop,
                        iconColor = colors.primary
                    )
                }
            } else {
                SkeletonBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }
    }
}
