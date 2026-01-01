package com.universall.watertracker.main.features.water_tracker.ui.water_view

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
import com.universall.watertracker.main.features.settings.domain.services_impl.SettingsServiceImplST
import com.universall.watertracker.main.features.water_tracker.domain.services_impl.WaterTrackerServiceImplST
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
    layoutPadding: PaddingValues,
    pagerNavigator: PagerRouterNavigator
) {
    val factory = remember {
        WaterTrackerViewModelFactory(
            waterTrackerService = WaterTrackerServiceImplST.get(),
            settingsService = SettingsServiceImplST.get()
        )
    }

    WaterView(
        viewModel = viewModel(factory = factory),
        layoutPadding = layoutPadding,
        pagerNavigator = pagerNavigator
    )
}
