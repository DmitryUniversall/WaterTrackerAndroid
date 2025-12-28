package com.universall.watertracker.main.common.entities

import java.time.LocalDate

data class WeekStatsShort(
    val start: LocalDate,
    val end: LocalDate,
    val weekTotal: Int,
    val daysStats: List<DayStatsShort>
)
