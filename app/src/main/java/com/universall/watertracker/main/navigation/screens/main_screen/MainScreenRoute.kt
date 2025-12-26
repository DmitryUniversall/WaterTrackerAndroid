package com.universall.watertracker.main.navigation.screens.main_screen

import com.universall.watertracker.core.ui.pager_router_screen.PagerRoute

sealed class MainScreenRoute(override val key: String) : PagerRoute {
    object Water : MainScreenRoute("water")
    object Stats : MainScreenRoute("stats")
    object Settings : MainScreenRoute("settings")

    companion object {
        val routes: List<MainScreenRoute> = listOf(Water, Stats, Settings)
    }
}
