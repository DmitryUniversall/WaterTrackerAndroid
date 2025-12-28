package com.universall.watertracker.main.features.water_tracker.domain.services

import com.universall.watertracker.main.common.entities.DayStatsShort
import kotlinx.coroutines.flow.Flow

interface WaterTrackerService {
    fun observeCurrentDayStatsShort(): Flow<DayStatsShort>
    suspend fun addWater(amount: Int)
}
