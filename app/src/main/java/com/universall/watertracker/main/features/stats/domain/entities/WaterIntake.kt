package com.universall.watertracker.main.features.stats.domain.entities

import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit
import java.time.LocalDateTime

data class WaterIntake(
    val id: Long,
    val amount: Int,
    val dateTime: LocalDateTime,
    val waterMeasureUnit: WaterMeasureUnit
)