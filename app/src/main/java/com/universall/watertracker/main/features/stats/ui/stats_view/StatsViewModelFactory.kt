package com.universall.watertracker.main.features.stats.ui.stats_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.universall.watertracker.main.features.notifications.domain.services.NotificationsService
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import com.universall.watertracker.main.features.stats.domain.services.StatsService

class StatsViewModelFactory(
    private val statsService: StatsService,
    private val settingsService: SettingsService,
    private val notificationsService: NotificationsService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StatsViewModel(
                statsService = statsService,
                settingsService = settingsService,
                notificationsService = notificationsService
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
