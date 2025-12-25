package com.universall.watertracker.main.features.settings.ui.settings_view

import android.content.Context
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
import com.universall.watertracker.main.features.settings.data.repositories.SettingsRepositoryImpl
import com.universall.watertracker.main.features.settings.domain.services_impl.SettingsServiceImpl
import com.universall.watertracker.main.features.settings.ui.settings_view.components.GeneralSettingsSelection
import com.universall.watertracker.main.features.settings.ui.settings_view.components.NotificationsSettingsSelection

@Composable
fun SettingsView(
    viewModel: SettingsViewModel,
    layoutPadding: PaddingValues
) {
    val settingsState by viewModel.settingsUIState.collectAsState()

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    GenericScrollablePage(layoutPadding = layoutPadding) {
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
                    measureUnit = settingsState.settingsState!!.waterMeasureUnit
                )
                NotificationsSettingsSelection(
                    viewModel = viewModel,
                    notificationsEnabled = settingsState.settingsState!!.notificationsEnabled,
                    reminderInterval = settingsState.settingsState!!.reminderInterval,
                    reminderTimeRange = settingsState.settingsState!!.reminderTimeRange,
                    notificationSound = settingsState.settingsState!!.notificationSound
                )
            }
        }
    }
}


@Composable
fun SettingsView(
    context: Context,
    layoutPadding: PaddingValues
) {
    val factory = remember {
        SettingsViewModelFactory(
            settingsService = SettingsServiceImpl(
                repository = SettingsRepositoryImpl(
                    context = context
                )
            )
        )
    }

    SettingsView(
        viewModel = viewModel(factory = factory),
        layoutPadding = layoutPadding
    )
}
