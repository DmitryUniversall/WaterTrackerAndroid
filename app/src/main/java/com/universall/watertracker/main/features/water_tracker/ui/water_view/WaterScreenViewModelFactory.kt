package com.universall.watertracker.main.features.water_tracker.ui.water_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import com.universall.watertracker.main.features.water_tracker.domain.services.WaterTrackerService

class WaterTrackerViewModelFactory(
    private val waterTrackerService: WaterTrackerService,
    private val settingsService: SettingsService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterTrackerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WaterTrackerViewModel(
                waterTrackerService = waterTrackerService,
                settingsService = settingsService
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
