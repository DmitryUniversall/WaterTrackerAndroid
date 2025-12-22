package com.universall.watertracker.main.features.settings.domain.repositories

import com.universall.watertracker.main.features.settings.domain.entities.SettingsState
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val settingsFlow: Flow<SettingsState>

    suspend fun setDailyGoal(amountMl: Int)
    suspend fun setAddButtonValue(amountMl: Int)
    suspend fun setRemindersEnabledGoal(enabled: Boolean)
}
