package com.universall.watertracker.main.features.water_tracker.ui.water_view

import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit

sealed interface UIState {
    object Loading : UIState

    data class Content(
        val waterAmountMl: Int,
        val dailyGoalMl: Int,
        val addButtonValue: Int,
        val waterMeasureUnit: WaterMeasureUnit
    ) : UIState
}
