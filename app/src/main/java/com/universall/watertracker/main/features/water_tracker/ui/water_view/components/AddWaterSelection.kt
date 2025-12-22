package com.universall.watertracker.main.features.water_tracker.ui.water_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Bold
import com.adamglin.phosphoricons.bold.Plus
import com.universall.watertracker.core.ui.SkeletonBox
import com.universall.watertracker.main.features.water_tracker.ui.water_view.UIState
import com.universall.watertracker.main.features.water_tracker.ui.water_view.WaterTrackerViewModel

@Composable
fun AddWaterSelection(
    viewModel: WaterTrackerViewModel,
    uiState: UIState
) {
    Column(
        modifier = Modifier
            .padding(bottom = 32.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(percent = 50)
                )
                .clip(
                    shape = RoundedCornerShape(percent = 50)
                )
                .clickable(
                    enabled = uiState !is UIState.Loading,
                    onClick = { viewModel.addWater() }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp),
                imageVector = PhosphorIcons.Bold.Plus,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        when(uiState) {
            is UIState.Loading -> {
                SkeletonBox(
                    modifier = Modifier
                        .width(56.dp)
                        .height(16.dp)
                        .testTag("remaining_output_skeleton"),
                    shape = RoundedCornerShape(8.dp)
                )
            }
            is UIState.Content -> {
                Text(
                    text = "+${uiState.addButtonValue} ml",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
