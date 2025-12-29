package com.universall.watertracker.main.features.settings.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.universall.watertracker.core.TimeRange
import com.universall.watertracker.core.enumFromId
import com.universall.watertracker.core.toHHMM
import com.universall.watertracker.main.features.settings.data.preferences.SettingsPreferences
import com.universall.watertracker.main.features.settings.data.preferences.settingsDataStore
import com.universall.watertracker.main.features.settings.domain.entities.NotificationSound
import com.universall.watertracker.main.features.settings.domain.entities.SettingsState
import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit
import com.universall.watertracker.main.features.settings.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.time.LocalTime

class SettingsRepositoryImpl(
    context: Context
) : SettingsRepository {
    private val dataStore = context.settingsDataStore

    private fun fromPrimitives(
        darkModeEnabled: Boolean?,
        dailyGoal: Int?,
        waterMeasureUnit: Int?,
        notificationsEnabled: Boolean?,
        reminderInterval: Int?,
        reminderTimeRangeStart: String?,
        reminderTimeRangeEnd: String?,
        notificationSound: Int?,
        addButtonValue: Int?,
        recordsSwipeable: Boolean?
    ): SettingsState {

        var reminderTimeRangeObj: TimeRange? = null
        if (reminderTimeRangeStart != null && reminderTimeRangeEnd != null) {
            reminderTimeRangeObj = TimeRange(
                start = LocalTime.parse(reminderTimeRangeStart),
                end = LocalTime.parse(reminderTimeRangeEnd)
            )
        }

        return SettingsState(
            darkModeEnabled = darkModeEnabled,
            dailyGoal = dailyGoal,
            notificationsEnabled = notificationsEnabled,
            reminderInterval = reminderInterval,
            addButtonValue = addButtonValue,
            reminderTimeRange = reminderTimeRangeObj,
            waterMeasureUnit = waterMeasureUnit?.let { enumFromId<WaterMeasureUnit>(waterMeasureUnit) },
            notificationSound = notificationSound?.let { enumFromId<NotificationSound>(notificationSound) },
            recordsSwipeable = recordsSwipeable
        )
    }

    override val settingsFlow: Flow<SettingsState> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences ->
            fromPrimitives(
                darkModeEnabled = preferences[SettingsPreferences.DARK_MODE_ENABLED],
                dailyGoal = preferences[SettingsPreferences.DAILY_GOAL],
                waterMeasureUnit = preferences[SettingsPreferences.WATER_MEASURE_UNIT],
                notificationsEnabled = preferences[SettingsPreferences.NOTIFICATIONS_ENABLED],
                reminderInterval = preferences[SettingsPreferences.REMINDER_INTERVAL],
                reminderTimeRangeStart = preferences[SettingsPreferences.REMINDER_TIME_RANGE_START],
                reminderTimeRangeEnd = preferences[SettingsPreferences.REMINDER_TIME_RANGE_END],
                notificationSound = preferences[SettingsPreferences.NOTIFICATION_SOUND],
                addButtonValue = preferences[SettingsPreferences.ADD_BUTTON_VALUE],
                recordsSwipeable = preferences[SettingsPreferences.RECORDS_SWIPEABLE]
            )
        }

    override suspend fun setDarkModeEnabled(enabled: Boolean) {
        dataStore.edit { it[SettingsPreferences.DARK_MODE_ENABLED] = enabled }
    }

    override suspend fun setDailyGoal(goal: Int) {
        dataStore.edit { it[SettingsPreferences.DAILY_GOAL] = goal }
    }

    override suspend fun setWaterMeasureUnit(waterMeasureUnit: WaterMeasureUnit) {
        dataStore.edit { it[SettingsPreferences.WATER_MEASURE_UNIT] = waterMeasureUnit.id }
    }

    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { it[SettingsPreferences.NOTIFICATIONS_ENABLED] = enabled }
    }

    override suspend fun setReminderInterval(remindersInterval: Int) {
        dataStore.edit { it[SettingsPreferences.REMINDER_INTERVAL] = remindersInterval }
    }

    override suspend fun setReminderTimeRange(timeRange: TimeRange) {
        dataStore.edit { it[SettingsPreferences.REMINDER_TIME_RANGE_START] = timeRange.start.toHHMM() }
        dataStore.edit { it[SettingsPreferences.REMINDER_TIME_RANGE_END] = timeRange.end.toHHMM() }
    }

    override suspend fun setNotificationSound(notificationSound: NotificationSound) {
        dataStore.edit { it[SettingsPreferences.NOTIFICATION_SOUND] = notificationSound.id }
    }

    override suspend fun setAddButtonValue(value: Int) {
        dataStore.edit { it[SettingsPreferences.ADD_BUTTON_VALUE] = value }
    }

    override suspend fun setRecordsSwipeable(enabled: Boolean) {
        dataStore.edit { it[SettingsPreferences.RECORDS_SWIPEABLE] = enabled }
    }
}
