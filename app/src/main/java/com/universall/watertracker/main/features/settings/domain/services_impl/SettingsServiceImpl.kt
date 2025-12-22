package com.universall.watertracker.main.features.settings.domain.services_impl

import com.universall.watertracker.main.features.settings.domain.entities.SettingsState
import com.universall.watertracker.main.features.settings.domain.repositories.SettingsRepository
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import kotlinx.coroutines.flow.Flow

class SettingsServiceImpl(
    private val repository: SettingsRepository
) : SettingsService {
    override val settingsFlow: Flow<SettingsState> = repository.settingsFlow

    override suspend fun setDailyGoal(amountMl: Int) {
        repository.setDailyGoal(amountMl = amountMl)
    }

    override suspend fun setAddButtonValue(amountMl: Int) {
        repository.setAddButtonValue(amountMl = amountMl)
    }

    override suspend fun setRemindersEnabledGoal(enabled: Boolean) {
        repository.setRemindersEnabledGoal(enabled = enabled)
    }
}
