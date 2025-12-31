package com.universall.watertracker.main.features.settings.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.settingsDataStore by preferencesDataStore(name = "settings")

object SettingsPreferences {
    val DARK_MODE_ENABLED = booleanPreferencesKey("dark_mode_enabled")
    val DAILY_GOAL = intPreferencesKey("daily_goal")
    val WATER_MEASURE_UNIT = intPreferencesKey("water_measure_unit")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    val REMINDER_INTERVAL = intPreferencesKey("reminder_interval")
    val REMINDER_TIME_RANGE_START = stringPreferencesKey("reminder_time_range_start")
    val REMINDER_TIME_RANGE_END = stringPreferencesKey("reminder_time_range_end")
    val NOTIFICATION_SOUND = intPreferencesKey("notification_sound")
    val ADD_BUTTON_VALUE = intPreferencesKey("add_button_value")
    val RECORDS_SWIPEABLE = booleanPreferencesKey("records_swipeable")
    val NOTIFICATIONS_CHANNEL_ID = stringPreferencesKey("notifications_channel_id")
}
