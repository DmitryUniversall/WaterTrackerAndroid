package com.universall.watertracker.main.features.stats.domain.services

import com.universall.watertracker.main.common.entities.DayStats
import com.universall.watertracker.main.common.entities.DayStatsShort
import com.universall.watertracker.main.common.entities.WeekStats
import com.universall.watertracker.main.common.entities.WeekStatsShort
import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface StatsService {
    fun observeDayStats(date: LocalDate): Flow<DayStats>
    fun observeCurrentDayStats(): Flow<DayStats>

    fun observeDayStatsShort(date: LocalDate): Flow<DayStatsShort>
    fun observeCurrentDayStatsShort(): Flow<DayStatsShort>

    fun observeWeekStats(date: LocalDate): Flow<WeekStats>
    fun observeCurrentWeekStats(): Flow<WeekStats>

    fun observeWeekStatsShort(date: LocalDate): Flow<WeekStatsShort>
    fun observeCurrentWeekStatsShort(): Flow<WeekStatsShort>

    suspend fun createIntake(amount: Int, measureUnit: WaterMeasureUnit)
}
