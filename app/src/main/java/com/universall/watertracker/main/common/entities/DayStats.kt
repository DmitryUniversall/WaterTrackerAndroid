package com.universall.watertracker.main.common.entities

import com.universall.watertracker.main.features.stats.domain.entities.WaterIntake
import java.time.LocalDate

data class DayStats(
    val date: LocalDate,
    val waterIntakes: List<WaterIntake>,
    val amountTotal: Int
)
