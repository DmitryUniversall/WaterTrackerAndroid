package com.universall.watertracker.main.navigation

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.universall.watertracker.main.navigation.screens.main_screen.MainScreen

@Composable
fun AppNavHost(
    context: Context
) {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = AppRoute.Main.routeName
    ) {
        composable(AppRoute.Main.routeName) { MainScreen(context) }
    }
}
