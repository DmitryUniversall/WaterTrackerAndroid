package com.universall.watertracker.main.features.settings.ui.settings_view.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.Alarm
import com.adamglin.phosphoricons.regular.Bell
import com.adamglin.phosphoricons.regular.Hourglass
import com.adamglin.phosphoricons.regular.SpeakerHigh
import com.universall.watertracker.R
import com.universall.watertracker.core.TimeRange
import com.universall.watertracker.main.features.notifications.NotificationsHelperImplST
import com.universall.watertracker.main.features.settings.domain.entities.NotificationSound
import com.universall.watertracker.main.features.settings.ui.settings_view.SettingsViewModel
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsBooleanField
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsEnumField
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsIntModalField
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsTimeRangeField

@Composable
fun NotificationsEnabledToggle(
    viewModel: SettingsViewModel,
    enabledInSettings: Boolean
) {
    var permissionRequestedByUser by remember { mutableStateOf(false) }
    val notificationsAllowed = NotificationsHelperImplST.get().isAllowedToSendNotifications()

    val permissionRequestLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            viewModel.setNotificationsEnabled(true)
        } else {
            viewModel.setNotificationsEnabled(false)
        }

        permissionRequestedByUser = false
    }

    LaunchedEffect(notificationsAllowed) {
        if (!notificationsAllowed && enabledInSettings && !permissionRequestedByUser) {
            viewModel.setNotificationsEnabled(false)
        }
    }

    SettingsBooleanField(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .height(30.dp),
        title = stringResource(R.string.notifications_setting_title),
        icon = PhosphorIcons.Regular.Bell,
        value = enabledInSettings,
        onSwitch = { checked ->
            if (!checked) {
                viewModel.setNotificationsEnabled(false)
            } else if (notificationsAllowed) {
                viewModel.setNotificationsEnabled(true)
            } else {
                permissionRequestedByUser = true
                permissionRequestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    )
}

@Composable
fun NotificationsSettingsSelection(
    viewModel: SettingsViewModel,
    notificationsEnabled: Boolean,
    reminderInterval: Int,
    reminderTimeRange: TimeRange?,
    notificationSound: NotificationSound
) {
    val colors = MaterialTheme.colorScheme

    SettingsSelection(
        title = stringResource(R.string.notifications_settings_selection),
        containerPadding = PaddingValues(24.dp)
    ) {
        NotificationsEnabledToggle(
            viewModel = viewModel,
            enabledInSettings = notificationsEnabled
        )

        HorizontalDivider(thickness = 1.dp, color = colors.onSurfaceVariant.copy(alpha = 0.7f))

        SettingsIntModalField(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .height(30.dp),
            title = stringResource(R.string.reminder_interval_setting_title),
            displayValue = "$reminderInterval min",
            icon = PhosphorIcons.Regular.Hourglass,
            onDismiss = { value ->
                if (value != null) viewModel.setReminderInterval(value)
            }
        )

        HorizontalDivider(thickness = 1.dp, color = colors.onSurfaceVariant.copy(alpha = 0.7f))

        SettingsTimeRangeField(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .height(30.dp),
            title = stringResource(R.string.notifications_time_range_setting_title),
            value = reminderTimeRange,
            icon = PhosphorIcons.Regular.Alarm,
            onDismiss = { timeRange ->
                if (timeRange != null) viewModel.setReminderTimeRange(timeRange)
            },
        )

        HorizontalDivider(thickness = 1.dp, color = colors.onSurfaceVariant.copy(alpha = 0.7f))

        SettingsEnumField(
            modifier = Modifier
                .padding(top = 12.dp)
                .height(30.dp),
            title = stringResource(R.string.notifications_sound_setting_title),
            icon = PhosphorIcons.Regular.SpeakerHigh,

            value = notificationSound,
            options = NotificationSound.entries.toTypedArray(),
            onValueSelected = { sound ->
                viewModel.setNotificationSound(sound)
            },
        )
    }
}
