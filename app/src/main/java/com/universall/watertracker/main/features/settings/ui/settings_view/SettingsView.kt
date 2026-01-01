package com.universall.watertracker.main.features.settings.ui.settings_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.universall.watertracker.main.common.ui.GenericScrollablePage
import com.universall.watertracker.main.features.settings.domain.services_impl.SettingsServiceImplST
import com.universall.watertracker.main.features.settings.ui.settings_view.components.GeneralSettingsSelection
import com.universall.watertracker.main.features.settings.ui.settings_view.components.MainScreenSettingsSelection
import com.universall.watertracker.main.features.settings.ui.settings_view.components.NotificationsSettingsSelection
import com.universall.watertracker.main.features.settings.ui.settings_view.components.StatsScreenSettingsSelection

@Composable
fun SettingsView(
    viewModel: SettingsViewModel,
    layoutPadding: PaddingValues
) {
    val settingsState by viewModel.settingsUIState.collectAsState()

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    GenericScrollablePage(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        layoutPadding = layoutPadding
    ) {
        Text(
            modifier = Modifier
                .padding(32.dp),
            text = "Settings",
            color = colors.onBackground,
            style = typography.headlineLarge
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            if (settingsState.isLoading) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                GeneralSettingsSelection(
                    viewModel = viewModel,
                    nightModeEnabled = settingsState.settingsState!!.darkModeEnabled,
                    dailyGoal = settingsState.settingsState!!.dailyGoal,
                    waterMeasureUnit = settingsState.settingsState!!.waterMeasureUnit
                )
                NotificationsSettingsSelection(
                    viewModel = viewModel,
                    notificationsEnabled = settingsState.settingsState!!.notificationsEnabled,
                    reminderInterval = settingsState.settingsState!!.reminderInterval,
                    reminderTimeRange = settingsState.settingsState!!.reminderTimeRange,
                    notificationSound = settingsState.settingsState!!.notificationSound
                )
                MainScreenSettingsSelection(
                    viewModel = viewModel,
                    addButtonValue = settingsState.settingsState!!.addButtonValue,
                    waterMeasureUnit = settingsState.settingsState!!.waterMeasureUnit
                )
                StatsScreenSettingsSelection(
                    viewModel = viewModel,
                    recordsSwipeable = settingsState.settingsState!!.recordsSwipeable
                )
            }
        }
    }
}


@Composable
fun SettingsView(
    layoutPadding: PaddingValues
) {
    val factory = remember {
        SettingsViewModelFactory(
            settingsService = SettingsServiceImplST.get()
        )
    }

    SettingsView(
        viewModel = viewModel(factory = factory),
        layoutPadding = layoutPadding
    )
}
