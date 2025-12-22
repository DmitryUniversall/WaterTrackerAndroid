package com.universall.watertracker.main.features.water_tracker.domain.repositories

import com.universall.watertracker.main.features.water_tracker.domain.entities.WaterTrackerDayState
import kotlinx.coroutines.flow.Flow

interface WaterTrackerRepository {
    fun observeCurrentDayState(): Flow<WaterTrackerDayState>
    suspend fun addWater(amountMl: Int)
}
