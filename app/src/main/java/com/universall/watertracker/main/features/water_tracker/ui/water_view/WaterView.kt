package com.universall.watertracker.main.features.water_tracker.ui.water_view

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.universall.watertracker.core.ui.pager_router_screen.PagerRouterNavigator
import com.universall.watertracker.main.common.db.AppDatabase
import com.universall.watertracker.main.features.settings.data.repositories.SettingsRepositoryImpl
import com.universall.watertracker.main.features.settings.domain.services_impl.SettingsServiceImpl
import com.universall.watertracker.main.features.stats.data.repositories.StatsRepositoryImpl
import com.universall.watertracker.main.features.stats.domain.services_impl.StatsServiceImpl
import com.universall.watertracker.main.features.water_tracker.domain.services_impl.WaterTrackerServiceImpl
import com.universall.watertracker.main.features.water_tracker.ui.water_view.components.AddWaterSelection
import com.universall.watertracker.main.features.water_tracker.ui.water_view.components.WaterStatusSelection


@Composable
fun WaterView(
    viewModel: WaterTrackerViewModel,
    layoutPadding: PaddingValues,
    pagerNavigator: PagerRouterNavigator
) {
    val uiState by viewModel.uiState.collectAsState()

    MainScreenBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(bottom = layoutPadding.calculateBottomPadding())
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 102.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    WaterStatusSelection(uiState = uiState, viewModel = viewModel, pagerNavigator = pagerNavigator)
                    AddWaterSelection(uiState = uiState, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun WaterView(
    context: Context,
    layoutPadding: PaddingValues,
    pagerNavigator: PagerRouterNavigator
) {
    val settingsService = remember {
        SettingsServiceImpl(
            repository = SettingsRepositoryImpl(context = context)
        )
    }

    val factory = remember {
        WaterTrackerViewModelFactory(
            waterTrackerService = WaterTrackerServiceImpl(
                statsService = StatsServiceImpl(
                    repository = StatsRepositoryImpl(
                        dao = AppDatabase.getInstance(context = context).waterIntakeDao()
                    )
                ),
                settingsService = settingsService
            ),
            settingsService = settingsService
        )
    }

    WaterView(
        viewModel = viewModel(factory = factory),
        layoutPadding = layoutPadding,
        pagerNavigator = pagerNavigator
    )
}
