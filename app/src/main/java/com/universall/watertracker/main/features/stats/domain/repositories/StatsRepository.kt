package com.universall.watertracker.main.features.stats.domain.repositories

import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit
import com.universall.watertracker.main.features.stats.domain.entities.WaterIntake
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface StatsRepository {
    fun observeWaterIntakesBetween(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<List<WaterIntake>>

    fun observeAmountTotalBetween(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<Int>

    suspend fun createIntake(
        amount: Int,
        measureUnit: WaterMeasureUnit
    )
}
