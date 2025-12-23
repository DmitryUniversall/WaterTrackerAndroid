package com.universall.watertracker.main.features.settings.ui.settings_view.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.Alarm
import com.adamglin.phosphoricons.regular.Bell
import com.adamglin.phosphoricons.regular.Drop
import com.universall.watertracker.core.asValidationResult
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsIntModalField


@Composable
fun GeneralSettingsSelection() {
    val colors = MaterialTheme.colorScheme

    SettingsSelection(
        title = "General"
    ) {
        Box() {  // TODO: Padding box
            SettingsIntModalField(
                title = "Daily goal",
                currentValue = "1500 ml",
                icon = PhosphorIcons.Regular.Drop,
                validators = listOf {
                    (it in 0..50000).asValidationResult()
                },
                onDismiss = { value -> Log.i("App", value?.toString() ?: "No value provided") }
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 1.dp, color = colors.onSurfaceVariant.copy(alpha = 0.7f))

        SettingsIntModalField(
            title = "Reminder interval",
            currentValue = "90 min",
            icon = PhosphorIcons.Regular.Bell,
            validators = listOf {
                (it in 0..50000).asValidationResult()
            },
            onDismiss = { value -> Log.i("App", value?.toString() ?: "No value provided") }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 1.dp, color = colors.onSurfaceVariant.copy(alpha = 0.7f))

        SettingsIntModalField(
            title = "Reminder times",
            currentValue = "7:00 am - 9:00 pm",
            icon = PhosphorIcons.Regular.Alarm,
            validators = listOf {
                (it in 0..50000).asValidationResult()
            },
            onDismiss = { value -> Log.i("App", value?.toString() ?: "No value provided") }
        )
    }
}