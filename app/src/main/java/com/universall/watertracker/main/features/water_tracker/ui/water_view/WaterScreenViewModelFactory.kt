package com.universall.watertracker.main.features.water_tracker.ui.water_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.universall.watertracker.main.features.water_tracker.domain.repositories.WaterTrackerRepository
import com.universall.watertracker.main.features.water_tracker.domain.services_impl.WaterTrackerServiceImpl

class WaterTrackerViewModelFactory(
    private val repository: WaterTrackerRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterTrackerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WaterTrackerViewModel(WaterTrackerServiceImpl(repository)) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
