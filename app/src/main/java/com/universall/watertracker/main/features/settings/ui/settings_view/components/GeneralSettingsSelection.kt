package com.universall.watertracker.main.features.settings.ui.settings_view.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.Drop
import com.adamglin.phosphoricons.regular.Moon
import com.adamglin.phosphoricons.regular.Ruler
import com.universall.watertracker.R
import com.universall.watertracker.core.asValidationResult
import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit
import com.universall.watertracker.main.features.settings.ui.settings_view.SettingsViewModel
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsBooleanField
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsEnumField
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsIntModalField


@Composable
fun GeneralSettingsSelection(
    viewModel: SettingsViewModel,
    nightModeEnabled: Boolean,
    dailyGoal: Int,
    waterMeasureUnit: WaterMeasureUnit
) {
    val colors = MaterialTheme.colorScheme

    SettingsSelection(
        title = "General",
        containerPadding = PaddingValues(24.dp)
    ) {
        SettingsBooleanField(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .height(30.dp),
            title = stringResource(R.string.dark_mode_setting_title),
            icon = PhosphorIcons.Regular.Moon,
            value = nightModeEnabled,
            onSwitch = { checked ->
                viewModel.setDarkModeEnabled(checked)
            }
        )

        HorizontalDivider(thickness = 1.dp, color = colors.onSurfaceVariant.copy(alpha = 0.7f))

        SettingsIntModalField(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .height(30.dp),
            title = stringResource(R.string.daily_goal_setting_title),
            displayValue = "$dailyGoal ${waterMeasureUnit.titleShort}",
            icon = PhosphorIcons.Regular.Drop,
            validators = listOf {
                (it in 0..50000).asValidationResult()
            },
            onDismiss = { value ->
                if (value != null) viewModel.setDailyGoal(value)
            }
        )

        HorizontalDivider(thickness = 1.dp, color = colors.onSurfaceVariant.copy(alpha = 0.7f))

        SettingsEnumField(
            modifier = Modifier
                .padding(top = 12.dp)
                .height(30.dp),
            title = stringResource(R.string.measure_unit_setting_title),
            icon = PhosphorIcons.Regular.Ruler,
            value = waterMeasureUnit,
            options = WaterMeasureUnit.entries.toTypedArray(),
            onValueSelected = { value ->
                viewModel.setWaterMeasureUnit(value)
            }
        )
    }
}
