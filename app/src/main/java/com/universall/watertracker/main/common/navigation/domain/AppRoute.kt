package com.universall.watertracker.main.common.navigation.domain

sealed class AppRoute(val route: String) {
    object Water : AppRoute("water")
    object Stats : AppRoute("stats")
    object Settings : AppRoute("settings")
}
