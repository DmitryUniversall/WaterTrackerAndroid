package com.universall.watertracker.main.navigation.screens.main_screen

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.universall.watertracker.core.ui.pager_router_screen.PagerRouterScreen
import com.universall.watertracker.core.ui.pager_router_screen.rememberPagerRouterScreenState
import com.universall.watertracker.main.features.settings.ui.settings_screen.SettingsScreen
import com.universall.watertracker.main.features.stats.ui.stats_screen.StatsScreen
import com.universall.watertracker.main.features.water_tracker.ui.water_view.WaterView
import com.universall.watertracker.main.navigation.screens.main_screen.components.BottomNavBar
import com.universall.watertracker.main.navigation.screens.main_screen.components.MainScreenBackground
import com.universall.watertracker.main.navigation.screens.main_screen.components.TopBar


@Composable
fun MainScreen(
    context: Context
) {
    val pagerRouter = rememberPagerRouterScreenState(
        MainBottomNavRoute.routes,
        startRoute = MainBottomNavRoute.Water
    )

    MainScreenBackground {
        Scaffold(
            modifier = Modifier.systemBarsPadding(),
            topBar = { TopBar(pagerRouter = pagerRouter) },
            bottomBar = { BottomNavBar(pagerRouter = pagerRouter) },
            containerColor = Color.Transparent
        ) { padding ->
            PagerRouterScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                state = pagerRouter
            ) {
                composable(MainBottomNavRoute.Water) { WaterView(context) }
                composable(MainBottomNavRoute.Stats) { StatsScreen() }
                composable(MainBottomNavRoute.Settings) { SettingsScreen() }
            }
        }
    }
}
