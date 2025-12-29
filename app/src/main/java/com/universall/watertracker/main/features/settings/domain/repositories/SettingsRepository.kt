package com.universall.watertracker.main.features.settings.domain.repositories

import com.universall.watertracker.core.TimeRange
import com.universall.watertracker.main.features.settings.domain.entities.NotificationSound
import com.universall.watertracker.main.features.settings.domain.entities.SettingsState
import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val settingsFlow: Flow<SettingsState>

    suspend fun setDarkModeEnabled(enabled: Boolean)
    suspend fun setDailyGoal(goal: Int)
    suspend fun setWaterMeasureUnit(waterMeasureUnit: WaterMeasureUnit)
    suspend fun setNotificationsEnabled(enabled: Boolean)
    suspend fun setReminderInterval(remindersInterval: Int)
    suspend fun setReminderTimeRange(timeRange: TimeRange)
    suspend fun setNotificationSound(notificationSound: NotificationSound)
    suspend fun setAddButtonValue(value: Int)
    suspend fun setRecordsSwipeable(enabled: Boolean)
}
