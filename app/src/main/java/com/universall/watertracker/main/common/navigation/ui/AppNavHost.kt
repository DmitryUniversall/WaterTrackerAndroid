package com.universall.watertracker.main.common.navigation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.universall.watertracker.main.common.navigation.domain.AppRoute
import com.universall.watertracker.main.common.navigation.ui.components.BottomNavBar
import com.universall.watertracker.main.common.navigation.ui.components.TopBar
import com.universall.watertracker.main.features.settings.ui.settings_screen.SettingsScreen
import com.universall.watertracker.main.features.stats.ui.stats_screen.StatsScreen
import com.universall.watertracker.main.features.water_tracker.ui.water_screen.WaterScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = { TopBar(navController = navController) },
        bottomBar = { BottomNavBar(navController = navController) },
        containerColor = Color.Transparent
    ) { padding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            navController = navController,
            startDestination = AppRoute.Water.route
        ) {
            composable(AppRoute.Water.route) { WaterScreen() }
            composable(AppRoute.Stats.route) { StatsScreen() }
            composable(AppRoute.Settings.route) { SettingsScreen() }
        }
    }
}
