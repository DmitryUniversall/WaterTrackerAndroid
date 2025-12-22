package com.universall.watertracker.main.features.stats.ui.stats_view.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Bold
import com.adamglin.phosphoricons.bold.Check

data class BarEntry(val label: String, val value: Float, val isHighlighted: Boolean = false)


@Composable
fun WeeklyBarChart(
    entries: List<BarEntry>,
    modifier: Modifier = Modifier,
    spacing: Dp = 10.dp
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val maxValue = (entries.maxOfOrNull { it.value } ?: 1f).coerceAtLeast(1f)

    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.Bottom
    ) {
        entries.forEach { entry ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    val fraction = (entry.value / maxValue).coerceIn(0f, 1f)

                    val animatedFraction by animateFloatAsState(
                        targetValue = fraction,
                        animationSpec = tween(
                            durationMillis = 600,
                            easing = FastOutSlowInEasing
                        ),
                        label = "barHeight"
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .fillMaxHeight(animatedFraction)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (entry.isHighlighted) colors.primary else colors.background)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = entry.value.toString(),
                            color = if (entry.isHighlighted) colors.onPrimary else colors.primary,
                            style = typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )

                        if (entry.isHighlighted) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = PhosphorIcons.Bold.Check,
                                contentDescription = null,
                                tint = colors.onPrimary
                            )
                        }
                    }
                }

                Text(
                    text = entry.label,
                    color = colors.onSurface,
                    style = typography.bodyMedium
                )
            }
        }
    }
}