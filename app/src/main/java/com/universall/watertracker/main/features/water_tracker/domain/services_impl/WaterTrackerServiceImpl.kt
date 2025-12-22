package com.universall.watertracker.main.features.water_tracker.domain.services_impl

import com.universall.watertracker.main.features.water_tracker.domain.entities.WaterTrackerDayState
import com.universall.watertracker.main.features.water_tracker.domain.repositories.WaterTrackerRepository
import com.universall.watertracker.main.features.water_tracker.domain.services.WaterTrackerService
import kotlinx.coroutines.flow.Flow

class WaterTrackerServiceImpl(
    private val repository: WaterTrackerRepository
) : WaterTrackerService {
    override fun observeCurrentDayState(): Flow<WaterTrackerDayState> {
        return repository.observeCurrentDayState()
    }

    override suspend fun addWater(amountMl: Int) {
        return repository.addWater(amountMl)
    }
}
