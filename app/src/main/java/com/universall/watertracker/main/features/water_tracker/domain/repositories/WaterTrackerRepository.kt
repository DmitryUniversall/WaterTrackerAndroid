package com.universall.watertracker.main.features.water_tracker.domain.repositories

import com.universall.watertracker.main.features.water_tracker.domain.entities.WaterTrackerState

interface WaterTrackerRepository {
    suspend fun saveState()
    suspend fun loadState(): WaterTrackerState

    fun getState(): WaterTrackerState
    fun setState(state: WaterTrackerState)
}
