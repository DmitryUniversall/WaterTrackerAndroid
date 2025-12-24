package com.universall.watertracker.main.features.settings.ui.settings_view.components

import android.util.Log
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
import com.adamglin.phosphoricons.regular.Drop
import com.adamglin.phosphoricons.regular.Moon
import com.adamglin.phosphoricons.regular.Ruler
import com.universall.watertracker.core.asValidationResult
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsBooleanField
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsIntModalField


@Composable
fun GeneralSettingsSelection() {
    val colors = MaterialTheme.colorScheme
    var checkedNightMode by remember { mutableStateOf(false) }

    SettingsSelection(
        title = "General",
        containerPadding = PaddingValues(24.dp)
    ) {
        SettingsBooleanField(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .height(30.dp),
            title = "Dark mode",
            icon = PhosphorIcons.Regular.Moon,
            value = checkedNightMode,
            onSwitch = { checked -> checkedNightMode = checked }
        )

        HorizontalDivider(thickness = 1.dp, color = colors.onSurfaceVariant.copy(alpha = 0.7f))

        SettingsIntModalField(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .height(30.dp),
            title = "Daily goal",
            value = "1500 ml",
            icon = PhosphorIcons.Regular.Drop,
            validators = listOf {
                (it in 0..50000).asValidationResult()
            },
            onDismiss = { value -> Log.i("App", value?.toString() ?: "No value provided") }
        )

        HorizontalDivider(thickness = 1.dp, color = colors.onSurfaceVariant.copy(alpha = 0.7f))

        SettingsIntModalField(
            modifier = Modifier
                .padding(top = 12.dp)
                .height(30.dp),
            title = "Unit",
            value = "Milliliters (ml)",
            icon = PhosphorIcons.Regular.Ruler,
            onDismiss = {}
        )
    }
}
