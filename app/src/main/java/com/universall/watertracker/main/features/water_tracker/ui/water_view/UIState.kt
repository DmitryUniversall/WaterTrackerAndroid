package com.universall.watertracker.main.features.water_tracker.ui.water_view

sealed interface UIState {
    object Loading : UIState

    data class Content(
        val waterAmount: Int
    ) : UIState
}
