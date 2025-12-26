package com.universall.watertracker.main.navigation.screens.main_screen.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.BookOpen
import com.adamglin.phosphoricons.regular.Drop
import com.adamglin.phosphoricons.regular.GearSix
import com.universall.watertracker.core.ui.pager_router_screen.PagerRouterNavigator
import com.universall.watertracker.main.navigation.screens.main_screen.MainScreenRoute


@Composable
fun BottomNavItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    val backgroundAlpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        ),
        label = "BackgroundAlpha"
    )

    val iconAlpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0.6f,
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        ),
        label = "IconAlpha"
    )

    Box(
        modifier = modifier
            .height(70.dp)
            .width(100.dp)
            .padding(8.dp)
            .background(
                color = colors.surfaceVariant.copy(alpha = backgroundAlpha),
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
            tint = colors.primary // if (selected) colors.primary.copy(alpha = iconAlpha) else colors.onPrimary.copy(alpha = iconAlpha)
        )
    }
}

@Composable
fun BottomNavBar(
    pagerRouter: PagerRouterNavigator,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Surface(
        modifier = modifier
            .padding(horizontal = 32.dp)
            .padding(bottom = 32.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(40.dp),
        color = colors.surface,
        shadowElevation = 4.dp,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavItem(
                icon = PhosphorIcons.Regular.Drop,
                selected = pagerRouter.currentRoute == MainScreenRoute.Water,
                onClick = {
                    pagerRouter.navigateTo(MainScreenRoute.Water)
                }
            )

            BottomNavItem(
                icon = PhosphorIcons.Regular.BookOpen,
                selected = pagerRouter.currentRoute == MainScreenRoute.Stats,
                onClick = {
                    pagerRouter.navigateTo(MainScreenRoute.Stats)
                }
            )

            BottomNavItem(
                icon = PhosphorIcons.Regular.GearSix,
                selected = pagerRouter.currentRoute == MainScreenRoute.Settings,
                onClick = {
                    pagerRouter.navigateTo(MainScreenRoute.Settings)
                }
            )
        }
    }
}
