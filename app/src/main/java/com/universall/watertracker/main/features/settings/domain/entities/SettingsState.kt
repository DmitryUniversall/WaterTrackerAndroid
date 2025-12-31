package com.universall.watertracker.main.features.settings.domain.entities

import com.universall.watertracker.core.TimeRange


object SettingsDefaults {
    const val DARK_MODE_ENABLED = false
    const val NOTIFICATIONS_ENABLED = false
    const val RECORDS_SWIPEABLE = true

    const val DAILY_GOAL = 1500
    const val REMINDER_INTERVAL = 90
    const val ADD_BUTTON_VALUE = 300

    val NOTIFICATIONS_CHANNEL_ID = null
    val REMINDER_TIME_RANGE = null
    val WATER_MEASURE_UNIT = WaterMeasureUnit.ML
    val NOTIFICATION_SOUND = NotificationSound.DING
}


data class SettingsState(
    val darkModeEnabled: Boolean,
    val dailyGoal: Int,
    val waterMeasureUnit: WaterMeasureUnit,

    val notificationsEnabled: Boolean,
    val reminderInterval: Int,
    val reminderTimeRange: TimeRange?,
    val notificationSound: NotificationSound,
    val notificationsChannelId: String?,

    val addButtonValue: Int,

    val recordsSwipeable: Boolean
) {
    constructor(
        darkModeEnabled: Boolean?,
        dailyGoal: Int?,
        waterMeasureUnit: WaterMeasureUnit?,
        notificationsEnabled: Boolean?,
        reminderInterval: Int?,
        reminderTimeRange: TimeRange?,
        notificationSound: NotificationSound?,
        addButtonValue: Int?,
        recordsSwipeable: Boolean?,
        notificationsChannelId: String?
    ) : this(
        darkModeEnabled = darkModeEnabled ?: SettingsDefaults.DARK_MODE_ENABLED,
        dailyGoal = dailyGoal ?: SettingsDefaults.DAILY_GOAL,
        waterMeasureUnit = waterMeasureUnit ?: SettingsDefaults.WATER_MEASURE_UNIT,
        notificationsEnabled = notificationsEnabled ?: SettingsDefaults.NOTIFICATIONS_ENABLED,
        reminderInterval = reminderInterval ?: SettingsDefaults.REMINDER_INTERVAL,
        reminderTimeRange = reminderTimeRange ?: SettingsDefaults.REMINDER_TIME_RANGE,
        notificationSound = notificationSound ?: SettingsDefaults.NOTIFICATION_SOUND,
        addButtonValue = addButtonValue ?: SettingsDefaults.ADD_BUTTON_VALUE,
        recordsSwipeable = recordsSwipeable ?: SettingsDefaults.RECORDS_SWIPEABLE,
        notificationsChannelId = notificationsChannelId ?: SettingsDefaults.NOTIFICATIONS_CHANNEL_ID
    )
}
