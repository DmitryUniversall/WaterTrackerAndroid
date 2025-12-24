package com.universall.watertracker.main.features.water_tracker.data.repositories

import com.universall.watertracker.core.todayTimestampRange
import com.universall.watertracker.main.features.water_tracker.data.db.dao.WaterIntakeDAO
import com.universall.watertracker.main.features.water_tracker.data.db.entities.WaterIntakeEntity
import com.universall.watertracker.main.features.water_tracker.domain.entities.WaterTrackerDayState
import com.universall.watertracker.main.features.water_tracker.domain.repositories.WaterTrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate


class WaterTrackerRepositoryImpl(
    private val dao: WaterIntakeDAO
) : WaterTrackerRepository {
    override fun observeCurrentDayState(): Flow<WaterTrackerDayState> {
        val (start, end) = todayTimestampRange()
        return dao.observeTotalBetween(start = start, end = end).map { totalMl ->
            WaterTrackerDayState(date = LocalDate.now(), waterAmountMl = totalMl)
        }
    }

    override suspend fun addWater(amountMl: Int) {
        dao.insert(
            WaterIntakeEntity(
                amountMl = amountMl,
                timestamp = System.currentTimeMillis()
            )
        )
    }
}
