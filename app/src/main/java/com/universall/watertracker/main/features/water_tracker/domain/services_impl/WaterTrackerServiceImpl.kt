package com.universall.watertracker.main.features.water_tracker.domain.services_impl

import com.universall.watertracker.main.features.water_tracker.domain.entities.WaterTrackerState
import com.universall.watertracker.main.features.water_tracker.domain.repositories.WaterTrackerRepository
import com.universall.watertracker.main.features.water_tracker.domain.services.WaterTrackerService

class WaterTrackerServiceImpl(
    private val repository: WaterTrackerRepository
) : WaterTrackerService {
    override suspend fun loadState(): WaterTrackerState {
        return repository.loadState()
    }

    override suspend fun addWater(amount: Int): WaterTrackerState {
        val state = repository.getState()
        val newState = state.copy(waterAmount = state.waterAmount + amount)

        repository.setState(newState)
        repository.saveState()

        return newState
    }
}
