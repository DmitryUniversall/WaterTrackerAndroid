package com.universall.watertracker.main.features.settings.domain.services_impl

import com.universall.watertracker.core.TimeRange
import com.universall.watertracker.main.features.settings.domain.entities.NotificationSound
import com.universall.watertracker.main.features.settings.domain.entities.SettingsState
import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit
import com.universall.watertracker.main.features.settings.domain.repositories.SettingsRepository
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import kotlinx.coroutines.flow.Flow

class SettingsServiceImpl(
    private val repository: SettingsRepository
) : SettingsService {
    override val settingsFlow: Flow<SettingsState> = repository.settingsFlow

    override suspend fun setDarkModeEnabled(enabled: Boolean) = repository.setDarkModeEnabled(enabled)
    override suspend fun setDailyGoal(goal: Int) = repository.setDailyGoal(goal)
    override suspend fun setWaterMeasureUnit(waterMeasureUnit: WaterMeasureUnit) = repository.setWaterMeasureUnit(waterMeasureUnit)
    override suspend fun setNotificationsEnabled(enabled: Boolean) = repository.setNotificationsEnabled(enabled)
    override suspend fun setReminderInterval(remindersInterval: Int) = repository.setReminderInterval(remindersInterval)
    override suspend fun setReminderTimeRange(timeRange: TimeRange) = repository.setReminderTimeRange(timeRange)
    override suspend fun setNotificationSound(notificationSound: NotificationSound) = repository.setNotificationSound(notificationSound)
    override suspend fun setAddButtonValue(value: Int) = repository.setAddButtonValue(value)
    override suspend fun setRecordsSwipeable(enabled: Boolean) = repository.setRecordsSwipeable(enabled)
}
