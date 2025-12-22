package com.universall.watertracker.main.features.water_tracker.domain.services

import com.universall.watertracker.main.features.water_tracker.domain.entities.WaterTrackerDayState
import kotlinx.coroutines.flow.Flow

interface WaterTrackerService {
    fun observeCurrentDayState(): Flow<WaterTrackerDayState>
    suspend fun addWater(amountMl: Int)
}
