package com.universall.watertracker.main.features.settings.ui.settings_view

import com.universall.watertracker.main.features.settings.domain.entities.SettingsState

data class SettingsUIState(
    val isLoading: Boolean,
    val settingsState: SettingsState? = null
) {
    companion object {
        val EmptyLoading = SettingsUIState(
            isLoading = true,
            settingsState = null
        )
    }
}
