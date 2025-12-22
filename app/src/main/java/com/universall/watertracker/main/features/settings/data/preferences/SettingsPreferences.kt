package com.universall.watertracker.main.features.settings.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.settingsDataStore by preferencesDataStore(name = "settings")

object SettingsPreferences {
    val DAILY_GOAL_ML = intPreferencesKey("daily_goal_ml")
    val ADD_BUTTON_VALUE = intPreferencesKey("add_button_value")
    val REMINDERS_ENABLED = booleanPreferencesKey("reminders_enabled")
}
