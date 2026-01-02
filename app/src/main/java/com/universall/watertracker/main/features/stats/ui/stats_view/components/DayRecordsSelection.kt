package com.universall.watertracker.main.features.stats.ui.stats_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Fill
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.fill.Drop
import com.adamglin.phosphoricons.regular.Clock
import com.universall.watertracker.R
import com.universall.watertracker.core.toHHMM
import com.universall.watertracker.core.ui.DottedVerticalSpacer
import com.universall.watertracker.core.ui.SkeletonBox
import com.universall.watertracker.main.common.entities.DayStats
import java.time.LocalDate
import java.time.LocalDateTime


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
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    selectedDay: LocalDate,
    selectedDayStats: DayStats?,
    nextReminderAt: LocalDateTime?
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = modifier
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
                text = stringResource(R.string.days_record),
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
            if (nextReminderAt != null && nextReminderAt.toLocalDate() == LocalDate.now()) {
                RecordBlock(
                    descriptionText = stringResource(R.string.next_reminder),
                    timeString = nextReminderAt.toLocalTime().toHHMM(),
                    icon = PhosphorIcons.Regular.Clock
                )

                if (selectedDayStats?.waterIntakes?.isNotEmpty() ?: false) {
                    DottedVerticalSpacer(
                        modifier = Modifier
                            .height(24.dp)
                            .padding(start = 15.dp)
                    )
                }
            }

            if (!isLoading) {
                if (selectedDayStats!!.waterIntakes.isNotEmpty()) {
                    selectedDayStats.waterIntakes.forEachIndexed { index, intake ->
                        RecordBlock(
                            descriptionText = "${intake.amount}${intake.waterMeasureUnit.titleShort}",
                            timeString = intake.dateTime.toLocalTime().toHHMM(),
                            icon = PhosphorIcons.Fill.Drop,
                            iconColor = colors.primary
                        )

                        if (index != selectedDayStats.waterIntakes.size - 1) {
                            DottedVerticalSpacer(
                                modifier = Modifier
                                    .height(24.dp)
                                    .padding(start = 15.dp)
                            )
                        }
                    }
                } else {
                    if (selectedDay != LocalDate.now()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.nothing_here),
                                color = colors.onSurface,
                                style = typography.titleLarge
                            )
                        }
                    }
                }
            } else {
                (1..4).forEach { _ ->
                    Spacer(
                        modifier = Modifier
                            .height(24.dp)
                            .padding(start = 15.dp)
                    )

                    SkeletonBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}
