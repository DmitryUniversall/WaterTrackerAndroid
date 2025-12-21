package com.universall.watertracker.core.ui.pager_router_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
class PagerRouterBuilder {
    internal val destinations = mutableMapOf<String, @Composable () -> Unit>()

    fun composable(route: PagerRoute, content: @Composable () -> Unit) {
        destinations[route.key] = content
    }
}
