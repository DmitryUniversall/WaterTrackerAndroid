package com.universall.watertracker.main.features.stats.ui.stats_view

import com.universall.watertracker.main.common.entities.DayStats
import com.universall.watertracker.main.common.entities.WeekStatsShort
import java.time.LocalDate

data class StatsUIState(
    val isLoading: Boolean,
    val selectedDay: LocalDate,
    val selectedDayStats: DayStats?,
    val weekStatsShort: WeekStatsShort?
) {
    companion object {
        val EmptyLoading = StatsUIState(
            isLoading = true,
            selectedDayStats = null,
            weekStatsShort = null,
            selectedDay = LocalDate.now()
        )
    }
}
