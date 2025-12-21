package com.universall.watertracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.universall.watertracker.core.ui.theme.WaterTrackerTheme
import com.universall.watertracker.main.common.navigation.ui.AppNavHost
import com.universall.watertracker.main.features.water_tracker.ui.water_screen.components.MainScreenBackground


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WaterTrackerTheme {
                MainScreenBackground {
                    AppNavHost()
                }
            }
        }
    }
}
