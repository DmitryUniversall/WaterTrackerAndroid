package com.universall.watertracker.main.features.water_tracker.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.waterTrackerDataStore by preferencesDataStore(name = "waterTracker")


object WaterTrackerPreferences {
    val WATER_AMOUNT = intPreferencesKey("water_amount")
}
