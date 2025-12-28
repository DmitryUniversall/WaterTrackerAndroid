package com.universall.watertracker.main.features.water_tracker.domain.services_impl

import com.universall.watertracker.main.common.entities.DayStatsShort
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import com.universall.watertracker.main.features.stats.domain.services.StatsService
import com.universall.watertracker.main.features.water_tracker.domain.services.WaterTrackerService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class WaterTrackerServiceImpl(
    private val statsService: StatsService,
    private val settingsService: SettingsService
) : WaterTrackerService {
    override fun observeCurrentDayStatsShort(): Flow<DayStatsShort> {
        return statsService.observeCurrentDayStatsShort()
    }

    override suspend fun addWater(amount: Int) {
        return statsService.createIntake(amount, settingsService.settingsFlow.first().waterMeasureUnit)
    }
}
