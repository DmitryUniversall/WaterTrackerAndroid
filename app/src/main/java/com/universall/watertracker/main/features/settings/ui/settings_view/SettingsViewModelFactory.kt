package com.universall.watertracker.main.features.settings.ui.settings_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.universall.watertracker.main.features.notifications.domain.services.NotificationsService
import com.universall.watertracker.main.features.settings.domain.services.SettingsService

class SettingsViewModelFactory(
    private val settingsService: SettingsService,
    private val notificationsService: NotificationsService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(
                settingsService = settingsService,
                notificationsService = notificationsService
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
