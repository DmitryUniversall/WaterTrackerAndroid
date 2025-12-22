package com.universall.watertracker.main.features.stats.ui.stats_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.universall.watertracker.main.features.stats.ui.stats_view.components.DayRecordsSelection
import com.universall.watertracker.main.features.stats.ui.stats_view.components.GraphSelection

@Composable
fun StatsView(
    interfacePadding: PaddingValues
) {
    val scrollState = rememberScrollState()
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.background)
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(bottom = interfacePadding.calculateBottomPadding())
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(32.dp),
                    text = "History",
                    color = colors.onBackground,
                    style = typography.headlineLarge
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    GraphSelection()
                    DayRecordsSelection()
                }
            }
        }
    }
}
