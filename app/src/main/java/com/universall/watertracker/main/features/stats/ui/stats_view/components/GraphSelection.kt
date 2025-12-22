package com.universall.watertracker.main.features.stats.ui.stats_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun GraphSelection() {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val sample = listOf(
        BarEntry("Mo", 1.6f),
        BarEntry("Tu", 1.4f),
        BarEntry("We", 2.2f, true),
        BarEntry("Th", 1.3f),
        BarEntry("Fr", 1.8f, true),
        BarEntry("Sa", 2.1f, true),
        BarEntry("Su", 1.5f),
    )

    Column(
        modifier = Modifier
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
                Text(
                    text = "1.84 L",
                    color = colors.primary,
                    style = typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
                )
            }

            WeeklyBarChart(
                modifier = Modifier
                    .weight(1f),
                entries = sample,
                spacing = 4.dp
            )
        }
    }
}
