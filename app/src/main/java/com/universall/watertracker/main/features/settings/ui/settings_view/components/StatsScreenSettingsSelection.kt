package com.universall.watertracker.main.features.settings.ui.settings_view.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.HandSwipeRight
import com.universall.watertracker.R
import com.universall.watertracker.main.features.settings.ui.settings_view.SettingsViewModel
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsBooleanField

@Composable
fun StatsScreenSettingsSelection(
    viewModel: SettingsViewModel,
    recordsSwipeable: Boolean
) {
    SettingsSelection(
        title = stringResource(R.string.stats_screen_settings_selection),
        containerPadding = PaddingValues(24.dp)
    ) {
        SettingsBooleanField(
            modifier = Modifier
                .height(30.dp),
            title = stringResource(R.string.records_swipeable_setting_title),
            icon = PhosphorIcons.Regular.HandSwipeRight,
            value = recordsSwipeable,
            onSwitch = { enabled -> viewModel.setRecordsSwipeable(enabled)}
        )
    }
}
