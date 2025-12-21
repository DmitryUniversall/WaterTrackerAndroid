package com.universall.watertracker.main.navigation.screens.main_screen

import com.universall.watertracker.core.ui.pager_router_screen.PagerRoute

sealed class MainBottomNavRoute(override val key: String) : PagerRoute {
    object Water : MainBottomNavRoute("water")
    object Stats : MainBottomNavRoute("stats")
    object Settings : MainBottomNavRoute("settings")

    companion object {
        val routes: List<MainBottomNavRoute> = listOf(Water, Stats, Settings)
    }
}
