package com.universall.watertracker.main.navigation.screens.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.universall.watertracker.R
import com.universall.watertracker.core.ui.pager_router_screen.PagerRoute
import com.universall.watertracker.core.ui.pager_router_screen.PagerRouterNavigator
import com.universall.watertracker.main.navigation.screens.main_screen.MainScreenRoute

@Composable
fun CurrentRouteTitle(
    route: PagerRoute
) {
    val text: String = when (route) {
        MainScreenRoute.Water -> stringResource(R.string.water_view_title)
        MainScreenRoute.Stats -> stringResource(R.string.stats_view_title)
        MainScreenRoute.Settings -> stringResource(R.string.settings_view_title)
        else -> stringResource(R.string.app_name)
    }

    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun TopBar(
    pagerRouter: PagerRouterNavigator
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrentRouteTitle(pagerRouter.currentRoute)
    }
}
