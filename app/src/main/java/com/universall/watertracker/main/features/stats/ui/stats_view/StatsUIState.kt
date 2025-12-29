package com.universall.watertracker.main.features.stats.ui.stats_view

import com.universall.watertracker.main.common.entities.WeekStats
import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit
import java.time.LocalDate

data class StatsUIState(
    val isLoading: Boolean,
    val selectedDay: LocalDate,
    val weekStats: WeekStats?,
    val dailyGoal: Int?,
    val waterMeasureUnit: WaterMeasureUnit?
) {
    companion object {
        val EmptyLoading = StatsUIState(
            isLoading = true,
            selectedDay = LocalDate.now(),
            weekStats = null,
            dailyGoal = null,
            waterMeasureUnit = null
        )
    }
}
