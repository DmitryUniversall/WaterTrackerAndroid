package com.universall.watertracker.main.features.water_tracker.ui.water_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universall.watertracker.main.features.water_tracker.domain.entities.WaterTrackerState
import com.universall.watertracker.main.features.water_tracker.domain.services.WaterTrackerService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WaterTrackerViewModel(
    private val service: WaterTrackerService
) : ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            updateUIState(
                state = service.loadState(),
                ignoreLoading = true
            )
        }
    }

    private fun updateUIState(state: WaterTrackerState, ignoreLoading: Boolean = false) {
        if (!ignoreLoading && _uiState.value is UIState.Loading) throw RuntimeException("Unable to update UI state: Still loading")

        _uiState.value = UIState.Content(
            waterAmount = state.waterAmount
        )
    }

    fun addWater() {
        viewModelScope.launch {
            val updatedState: WaterTrackerState = service.addWater(300)
            updateUIState(updatedState)
        }
    }
}
