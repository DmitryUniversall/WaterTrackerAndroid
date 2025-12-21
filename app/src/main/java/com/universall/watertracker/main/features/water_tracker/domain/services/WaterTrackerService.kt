package com.universall.watertracker.main.features.water_tracker.domain.services

import com.universall.watertracker.main.features.water_tracker.domain.entities.WaterTrackerState

interface WaterTrackerService {
    suspend fun loadState(): WaterTrackerState
    suspend fun addWater(amount: Int): WaterTrackerState
}
