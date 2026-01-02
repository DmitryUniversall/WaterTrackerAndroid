package com.universall.watertracker.main.features.settings.ui.settings_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universall.watertracker.core.TimeRange
import com.universall.watertracker.main.features.notifications.domain.services.NotificationsService
import com.universall.watertracker.main.features.settings.domain.entities.NotificationSound
import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsService: SettingsService,
    private val notificationsService: NotificationsService
) : ViewModel() {
    private val _settingsUIState: MutableStateFlow<SettingsUIState> = MutableStateFlow(SettingsUIState.EmptyLoading)
    val settingsUIState = _settingsUIState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsService.settingsFlow.collect { settingsState ->
                _settingsUIState.value = _settingsUIState.value.copy(
                    isLoading = false,
                    settingsState = settingsState
                )
            }
        }
    }

    fun isAllowedToSendNotifications()  = notificationsService.isAllowedToSendNotifications()

    fun setDarkModeEnabled(enabled: Boolean) = viewModelScope.launch { settingsService.setDarkModeEnabled(enabled) }
    fun setDailyGoal(goal: Int) = viewModelScope.launch { settingsService.setDailyGoal(goal) }
    fun setWaterMeasureUnit(waterMeasureUnit: WaterMeasureUnit) = viewModelScope.launch { settingsService.setWaterMeasureUnit(waterMeasureUnit) }
    fun setNotificationsEnabled(enabled: Boolean) = viewModelScope.launch { settingsService.setNotificationsEnabled(enabled) }
    fun setReminderInterval(remindersInterval: Int) = viewModelScope.launch { settingsService.setReminderInterval(remindersInterval) }
    fun setReminderTimeRange(timeRange: TimeRange) = viewModelScope.launch { settingsService.setReminderTimeRange(timeRange) }
    fun setNotificationSound(notificationSound: NotificationSound) = viewModelScope.launch { settingsService.setNotificationSound(notificationSound) }
    fun setAddButtonValue(value: Int) = viewModelScope.launch { settingsService.setAddButtonValue(value) }
    fun setRecordsSwipeable(enabled: Boolean) = viewModelScope.launch { settingsService.setRecordsSwipeable(enabled) }
}
