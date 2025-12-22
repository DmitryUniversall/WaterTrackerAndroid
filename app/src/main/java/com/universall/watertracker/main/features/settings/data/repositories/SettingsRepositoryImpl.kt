package com.universall.watertracker.main.features.settings.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.universall.watertracker.main.features.settings.data.preferences.SettingsPreferences
import com.universall.watertracker.main.features.settings.data.preferences.settingsDataStore
import com.universall.watertracker.main.features.settings.domain.entities.SettingsState
import com.universall.watertracker.main.features.settings.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    context: Context
) : SettingsRepository {
    private val dataStore = context.settingsDataStore

    override val settingsFlow: Flow<SettingsState> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences ->
            SettingsState(
                dailyGoalMl = preferences[SettingsPreferences.DAILY_GOAL_ML],
                remindersEnabled = preferences[SettingsPreferences.REMINDERS_ENABLED]
            )
        }

    override suspend fun setDailyGoal(amountMl: Int) {
        dataStore.edit { it[SettingsPreferences.DAILY_GOAL_ML] = amountMl }
    }

    override suspend fun setAddButtonValue(amountMl: Int) {
        dataStore.edit { it[SettingsPreferences.ADD_BUTTON_VALUE] = amountMl }
    }

    override suspend fun setRemindersEnabledGoal(enabled: Boolean) {
        dataStore.edit { it[SettingsPreferences.REMINDERS_ENABLED] = enabled }
    }
}
