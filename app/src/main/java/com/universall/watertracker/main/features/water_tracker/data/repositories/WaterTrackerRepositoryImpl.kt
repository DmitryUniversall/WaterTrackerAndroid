package com.universall.watertracker.main.features.water_tracker.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.universall.watertracker.main.features.water_tracker.domain.entities.WaterTrackerState
import com.universall.watertracker.main.features.water_tracker.domain.repositories.WaterTrackerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first


class WaterTrackerRepositoryImpl(
    private val context: Context
) : WaterTrackerRepository {
    private var waterAmount: Int? = null

    private fun currentValuesAsState(): WaterTrackerState {
        return WaterTrackerState(
            waterAmount = waterAmount
        )
    }

    override suspend fun saveState() {
        context.waterTrackerDataStore.edit {
            if (waterAmount != null) it[WaterTrackerPreferences.WATER_AMOUNT] = waterAmount!!
        }
    }

    override suspend fun loadState(): WaterTrackerState {
        delay(1000)

        val preferences = context.waterTrackerDataStore.data.first()
        waterAmount = preferences[WaterTrackerPreferences.WATER_AMOUNT]
        return currentValuesAsState()
    }

    override fun getState(): WaterTrackerState {
        return currentValuesAsState()
    }

    override fun setState(state: WaterTrackerState) {
        waterAmount = state.waterAmount
    }
}
