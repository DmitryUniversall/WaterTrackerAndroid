package com.universall.watertracker.main.features.settings.ui.settings_view.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.Alarm
import com.adamglin.phosphoricons.regular.Bell
import com.adamglin.phosphoricons.regular.Hourglass
import com.adamglin.phosphoricons.regular.SpeakerHigh
import com.universall.watertracker.core.TimeRange
import com.universall.watertracker.main.features.settings.domain.entities.NotificationSound
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsBooleanField
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsEnumField
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsIntModalField
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsTimeRangeField
import java.time.LocalTime

@Composable
fun NotificationsSettingsSelection() {
    val colors = MaterialTheme.colorScheme

    var checkedReminders by remember { mutableStateOf(false) }
    var reminderInterval by remember { mutableStateOf(90) }
    var notificationSound by remember { mutableStateOf(NotificationSound.FRESH) }
    var remindersTimeRange by remember {
        mutableStateOf(
            TimeRange(
                start = LocalTime.now(),
                end = LocalTime.now().plusHours(2)
            )
        )
    }

    SettingsSelection(
        title = "Notifications",
        containerPadding = PaddingValues(24.dp)
    ) {
        SettingsBooleanField(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .height(30.dp),
            title = "Notifications",
            icon = PhosphorIcons.Regular.Bell,
            value = checkedReminders,
            onSwitch = { checked ->
                checkedReminders = checked
            }
        )

        HorizontalDivider(thickness = 1.dp, color = colors.onSurfaceVariant.copy(alpha = 0.7f))

        SettingsIntModalField(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .height(30.dp),
            title = "Interval",
            displayValue = "$reminderInterval min",
            icon = PhosphorIcons.Regular.Hourglass,
            onDismiss = { value ->
                if (value != null) reminderInterval
            }
        )

        HorizontalDivider(thickness = 1.dp, color = colors.onSurfaceVariant.copy(alpha = 0.7f))

        SettingsTimeRangeField(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .height(30.dp),
            title = "Time range",
            value = remindersTimeRange,
            icon = PhosphorIcons.Regular.Alarm,
            onDismiss = { timeRange ->
                if (timeRange != null) remindersTimeRange = timeRange
            },
        )

        HorizontalDivider(thickness = 1.dp, color = colors.onSurfaceVariant.copy(alpha = 0.7f))

        SettingsEnumField(
            modifier = Modifier
                .padding(top = 12.dp)
                .height(30.dp),
            title = "Sound",
            icon = PhosphorIcons.Regular.SpeakerHigh,

            value = notificationSound,
            options = NotificationSound.entries.toTypedArray(),
            onValueSelected = { sound ->
                notificationSound = sound
            },
        )
    }
}
