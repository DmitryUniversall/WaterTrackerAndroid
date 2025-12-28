package com.universall.watertracker.main.common.entities

import java.time.LocalDate

data class DayStatsShort(
    val date: LocalDate,
    val amountTotal: Int
)
