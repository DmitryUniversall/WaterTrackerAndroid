package com.universall.watertracker

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.universall.watertracker.main.common.ui.theme.WaterTrackerTheme
import com.universall.watertracker.main.navigation.AppNavHost


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val context: Context = this

        setContent {
            WaterTrackerTheme {
                AppNavHost(context)
            }
        }
    }
}
