package com.universall.watertracker.main.features.stats.ui.stats_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.Clock
import com.universall.watertracker.R
import com.universall.watertracker.core.toHHMM
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun NextNotificationSelection(
    modifier: Modifier = Modifier,
    nextReminderAt: LocalDateTime
) {
    val colors = MaterialTheme.colorScheme
    val locale: Locale = LocalConfiguration.current.locales[0]

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
        RecordBlock(
            descriptionText = stringResource(R.string.next_reminder),
            timeString = "${nextReminderAt.dayOfWeek.getDisplayName(TextStyle.FULL, locale)}, ${nextReminderAt.toLocalTime().toHHMM()}",
            icon = PhosphorIcons.Regular.Clock
        )
    }
}
