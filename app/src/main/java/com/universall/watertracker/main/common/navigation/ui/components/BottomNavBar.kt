package com.universall.watertracker.main.common.navigation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.BookOpen
import com.adamglin.phosphoricons.regular.Drop
import com.adamglin.phosphoricons.regular.GearSix
import com.universall.watertracker.main.common.navigation.domain.AppRoute


@Composable
fun BottomNavItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .height(70.dp)
            .width(100.dp)
            .padding(8.dp)
            .background(
                color = if (selected) colors.primaryContainer else Color.Transparent,
                shape = RoundedCornerShape(percent = 50)
            )
            .clip(RoundedCornerShape(percent = 50))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = icon,
            contentDescription = null,
            tint = if (selected) colors.primary else colors.onPrimary
        )
    }
}

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavItem(
            icon = PhosphorIcons.Regular.Drop,
            selected = currentRoute == AppRoute.Water.route,
            onClick = {
                navController.navigate(AppRoute.Water.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavItem(
            icon = PhosphorIcons.Regular.BookOpen,
            selected = currentRoute == AppRoute.Stats.route,
            onClick = {
                navController.navigate(AppRoute.Stats.route) {
                    launchSingleTop = true
                }
            }
        )

        BottomNavItem(
            icon = PhosphorIcons.Regular.GearSix,
            selected = currentRoute == AppRoute.Settings.route,
            onClick = {
                navController.navigate(AppRoute.Settings.route) {
                    launchSingleTop = true
                }
            }
        )
    }
}
