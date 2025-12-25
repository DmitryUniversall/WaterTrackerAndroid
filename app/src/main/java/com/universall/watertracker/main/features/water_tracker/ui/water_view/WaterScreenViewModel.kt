package com.universall.watertracker.main.features.water_tracker.ui.water_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import com.universall.watertracker.main.features.water_tracker.domain.services.WaterTrackerService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WaterTrackerViewModel(
    private val waterTrackerService: WaterTrackerService,
    private val settingsService: SettingsService
) : ViewModel() {

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                waterTrackerService.observeCurrentDayState(),
                settingsService.settingsFlow
            ) { dayState, settings ->
                UIState.Content(
                    waterAmountMl = dayState.waterAmountMl,
                    dailyGoalMl = settings.dailyGoal,
                    addButtonValue = settings.addButtonValue
                )
            }.collect { content -> _uiState.value = content }
        }
    }

    fun addWater() {
        if (_uiState.value is UIState.Loading) throw RuntimeException("Unable to add water when UI is loading")
        viewModelScope.launch {
            val currentSettings = settingsService.settingsFlow.first()
            waterTrackerService.addWater(currentSettings.addButtonValue)
        }
    }

    fun setAddWaterValue(value: Int) {
        viewModelScope.launch {
            settingsService.setAddButtonValue(value)
        }
    }
}
