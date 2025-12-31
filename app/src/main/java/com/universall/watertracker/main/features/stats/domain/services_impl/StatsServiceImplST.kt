package com.universall.watertracker.main.features.stats.domain.services_impl

import com.universall.watertracker.core.SingletonHolder
import com.universall.watertracker.core.datesUntilInclusive
import com.universall.watertracker.core.dayBounds
import com.universall.watertracker.core.weekBounds
import com.universall.watertracker.main.common.entities.DayStats
import com.universall.watertracker.main.common.entities.DayStatsShort
import com.universall.watertracker.main.common.entities.WeekStats
import com.universall.watertracker.main.common.entities.WeekStatsShort
import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit
import com.universall.watertracker.main.features.stats.domain.repositories.StatsRepository
import com.universall.watertracker.main.features.stats.domain.services.StatsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class StatsServiceImplST private constructor(
    private val repository: StatsRepository
) : StatsService {
    override fun observeDayStats(date: LocalDate): Flow<DayStats> {
        val (start, end) = date.dayBounds()

        return repository.observeWaterIntakesBetween(start, end)
            .map { intakes ->
                DayStats(date = date, waterIntakes = intakes, amountTotal = intakes.sumOf { it.amount })
            }
    }

    override fun observeCurrentDayStats(): Flow<DayStats> {
        return observeDayStats(date = LocalDate.now())
    }

    override fun observeDayStatsShort(date: LocalDate): Flow<DayStatsShort> {
        val (start, end) = date.dayBounds()

        return repository.observeAmountTotalBetween(start, end)
            .map { total ->
                DayStatsShort(date = date, amountTotal = total)
            }
    }

    override fun observeCurrentDayStatsShort(): Flow<DayStatsShort> {
        return observeDayStatsShort(date = LocalDate.now())
    }

    override fun observeWeekStats(date: LocalDate): Flow<WeekStats> {
        val (weekStart, weekEnd) = date.weekBounds()

        return repository.observeWaterIntakesBetween(weekStart, weekEnd)
            .map { intakes ->
                val grouped = intakes.groupBy { intake ->
                    intake.dateTime.toLocalDate()
                }

                val stats = grouped.toSortedMap().map { (date, intakes) ->
                    DayStats(date = date, waterIntakes = intakes, amountTotal = intakes.sumOf { it.amount })
                }

                val daysStatsFinal = weekStart.toLocalDate().datesUntilInclusive(weekEnd.toLocalDate())
                    .map { date ->
                        val dayStats = stats.firstOrNull { it.date.dayOfWeek == date.dayOfWeek }
                        dayStats ?: DayStats(date = date, waterIntakes = emptyList(), amountTotal = 0)
                    }.toList()

                WeekStats(
                    start = weekStart.toLocalDate(),
                    end = weekEnd.toLocalDate(),
                    weekTotal = daysStatsFinal.sumOf { it.amountTotal },
                    daysStats = daysStatsFinal
                )
            }
    }

    override fun observeCurrentWeekStats(): Flow<WeekStats> {
        return observeWeekStats(date = LocalDate.now())
    }

    override fun observeWeekStatsShort(date: LocalDate): Flow<WeekStatsShort> {  // TODO: Optimize it via db query
        val (weekStart, weekEnd) = date.weekBounds()

        return repository.observeWaterIntakesBetween(weekStart, weekEnd).map { intakes ->
            val grouped = intakes.groupBy { intake ->
                intake.dateTime.toLocalDate()
            }

            val daysStats = grouped.toSortedMap().map { (date, intakes) ->
                DayStatsShort(date = date, amountTotal = intakes.sumOf { it.amount })
            }

            WeekStatsShort(
                start = weekStart.toLocalDate(),
                end = weekEnd.toLocalDate(),
                weekTotal = daysStats.sumOf { it.amountTotal },
                daysStats = daysStats
            )
        }
    }

    override fun observeCurrentWeekStatsShort(): Flow<WeekStatsShort> {
        return observeWeekStatsShort(date = LocalDate.now())
    }

    override suspend fun createIntake(amount: Int, measureUnit: WaterMeasureUnit) {
        return repository.createIntake(amount, measureUnit)
    }

    companion object : SingletonHolder<StatsServiceImplST, StatsRepository>(::StatsServiceImplST)
}
