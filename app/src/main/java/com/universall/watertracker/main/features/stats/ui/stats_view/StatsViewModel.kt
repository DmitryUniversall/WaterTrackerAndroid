package com.universall.watertracker.main.features.stats.ui.stats_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import com.universall.watertracker.main.features.stats.domain.services.StatsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate

class StatsViewModel(
    private val statsService: StatsService,
    private val settingsService: SettingsService
) : ViewModel() {
    private val _uiState = MutableStateFlow(StatsUIState.EmptyLoading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                statsService.observeCurrentWeekStats(),
                settingsService.settingsFlow
            ) { weekStats, settings ->
                StatsUIState(
                    isLoading = false,
                    selectedDay = _uiState.value.selectedDay,
                    weekStats = weekStats,
                    dailyGoal = settings.dailyGoal,
                    waterMeasureUnit = settings.waterMeasureUnit,
                    recordsSwipeable = settings.recordsSwipeable
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    fun setSelectedDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(selectedDay = date)
    }
}
