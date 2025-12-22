package com.universall.watertracker.main.features.water_tracker.domain.entities

import java.time.LocalDate

data class WaterTrackerDayState(
    val date: LocalDate,
    val waterAmountMl: Int
)
