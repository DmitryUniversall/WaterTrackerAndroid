package com.universall.watertracker.main.features.stats.ui.stats_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.universall.watertracker.core.datesUntilInclusive
import com.universall.watertracker.core.toTwoDigits
import com.universall.watertracker.core.twoLetterWeekDay
import com.universall.watertracker.core.ui.SkeletonBox
import com.universall.watertracker.core.weekBounds
import com.universall.watertracker.main.common.entities.WeekStats
import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit
import java.time.LocalDate


@Composable
fun GraphSelection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    selectedDay: LocalDate,
    dailyGoal: Int?,
    waterMeasureUnit: WaterMeasureUnit?,
    weekStats: WeekStats?,
    setSelectedDay: (LocalDate) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val (weekStart, weekEnd) = LocalDate.now().weekBounds()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(shape = RoundedCornerShape(24.dp))
            .background(
                color = colors.surface,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                if (!isLoading) {
                    Text(
                        text = "${weekStats!!.weekTotal} ${waterMeasureUnit!!.titleShort}",
                        color = colors.primary,
                        style = typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
                    )
                } else {
                    SkeletonBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                    )
                }
            }

            if (!isLoading) {
                WeeklyBarChart(
                    modifier = Modifier
                        .weight(1f),
                    entries = weekStart.toLocalDate()
                        .datesUntilInclusive(weekEnd.toLocalDate())
                        .map { date ->
                            val dayStats = weekStats!!.daysStats
                                .firstOrNull { it.date.dayOfWeek == date.dayOfWeek }

                            BarEntry(
                                label = date.twoLetterWeekDay(),
                                value = dayStats?.amountTotal?.let { (it.toDouble() / 1000).toTwoDigits().toFloat() } ?: 0f,
                                isHighlighted = dayStats?.amountTotal?.let { it >= dailyGoal!! } ?: false,
                                isSelected = selectedDay == date,
                                onBarClick = { setSelectedDay(date) }
                            )
                        }.toList(),
                    spacing = 4.dp
                )
            } else {
                SkeletonBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }
        }
    }
}
