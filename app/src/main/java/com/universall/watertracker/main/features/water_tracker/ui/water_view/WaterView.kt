package com.universall.watertracker.main.features.water_tracker.ui.water_view

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.universall.watertracker.main.common.db.AppDatabase
import com.universall.watertracker.main.features.settings.data.repositories.SettingsRepositoryImpl
import com.universall.watertracker.main.features.settings.domain.services_impl.SettingsServiceImpl
import com.universall.watertracker.main.features.water_tracker.data.repositories.WaterTrackerRepositoryImpl
import com.universall.watertracker.main.features.water_tracker.domain.services_impl.WaterTrackerServiceImpl
import com.universall.watertracker.main.features.water_tracker.ui.water_view.components.AddWaterSelection
import com.universall.watertracker.main.features.water_tracker.ui.water_view.components.WaterStatusSelection


@Composable
fun WaterView(
    viewModel: WaterTrackerViewModel
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        WaterStatusSelection(uiState = uiState)
        AddWaterSelection(uiState = uiState, viewModel = viewModel)
    }
}

@Composable
fun WaterView(context: Context) {
    val factory = remember {
        WaterTrackerViewModelFactory(
            waterTrackerService = WaterTrackerServiceImpl(
                repository = WaterTrackerRepositoryImpl(
                    dao = AppDatabase.getInstance(context = context).waterIntakeDao()
                )
            ),
            settingsService = SettingsServiceImpl(
                repository = SettingsRepositoryImpl(context = context)
            )
        )
    }

    WaterView(viewModel(factory = factory))
}
