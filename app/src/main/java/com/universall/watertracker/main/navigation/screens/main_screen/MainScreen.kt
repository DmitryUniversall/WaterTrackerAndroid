package com.universall.watertracker.main.navigation.screens.main_screen

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.universall.watertracker.core.ui.pager_router_screen.PagerRouterScreen
import com.universall.watertracker.core.ui.pager_router_screen.rememberPagerRouterScreenState
import com.universall.watertracker.main.features.settings.ui.settings_view.SettingsView
import com.universall.watertracker.main.features.stats.ui.stats_view.StatsView
import com.universall.watertracker.main.features.water_tracker.ui.water_view.WaterView
import com.universall.watertracker.main.navigation.screens.main_screen.components.BottomNavBar


@Composable
fun MainScreen(
    context: Context
) {
    val router = rememberPagerRouterScreenState(
        MainScreenRoute.routes,
        startRoute = MainScreenRoute.Water
    )

    Scaffold(
        bottomBar = { BottomNavBar(pagerRouter = router) },
        containerColor = Color.Transparent
    ) { padding ->
        PagerRouterScreen(
            modifier = Modifier.fillMaxSize(),
            state = router
        ) {
            composable(MainScreenRoute.Water) { WaterView(context, layoutPadding = padding, pagerNavigator = router) }
            composable(MainScreenRoute.Stats) { StatsView(layoutPadding = padding) }
            composable(MainScreenRoute.Settings) { SettingsView(context, layoutPadding = padding) }
        }
    }
}
