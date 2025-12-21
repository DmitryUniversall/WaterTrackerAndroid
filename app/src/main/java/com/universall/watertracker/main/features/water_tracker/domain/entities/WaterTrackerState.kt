package com.universall.watertracker.main.features.water_tracker.domain.entities

object WaterTrackerStateDefaults {
    const val WATER_AMOUNT = 0
}


data class WaterTrackerState(
    val waterAmount: Int,
) {
    constructor(
        waterAmount: Int?,
    ) : this(
        waterAmount ?: WaterTrackerStateDefaults.WATER_AMOUNT,
    )
}
