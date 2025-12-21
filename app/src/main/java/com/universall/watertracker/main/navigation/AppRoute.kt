package com.universall.watertracker.main.navigation

sealed class AppRoute(val routeName: String) {
    object Main : AppRoute("main")
}
