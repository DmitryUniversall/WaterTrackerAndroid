package com.universall.watertracker.main.features.water_tracker.ui.water_view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.universall.watertracker.R
import com.universall.watertracker.core.asValidationResult
import com.universall.watertracker.core.ui.SkeletonBox
import com.universall.watertracker.core.ui.dialogs.NumberInputDialog
import com.universall.watertracker.main.features.water_tracker.ui.water_view.UIState
import com.universall.watertracker.main.features.water_tracker.ui.water_view.WaterTrackerViewModel


@Composable
private fun WaterStatusBlock(
    uiState: UIState
) {
    Row(verticalAlignment = Alignment.Bottom) {
        when (uiState) {
            is UIState.Loading -> {
                SkeletonBox(
                    modifier = Modifier
                        .width(220.dp)
                        .height(90.dp)
                        .testTag("water_amount_output_skeleton"),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            is UIState.Content -> {
                Text(
                    text = uiState.waterAmountMl.toString(),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = stringResource(R.string.ml),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun WaterRemainingBlock(
    uiState: UIState,
    viewModel: WaterTrackerViewModel
) {
    var showEditDialog by remember { mutableStateOf(false) }

    when (uiState) {
        is UIState.Loading -> {
            SkeletonBox(
                modifier = Modifier
                    .width(136.dp)
                    .height(20.dp)
                    .testTag("remaining_output_skeleton"),
                shape = RoundedCornerShape(8.dp)
            )
        }

        is UIState.Content -> {
            val remaining: Int = uiState.dailyGoalMl - uiState.waterAmountMl
            Text(
                modifier = Modifier
                    .clickable(onClick = { showEditDialog = true }),
                text = "${stringResource(R.string.remaining)} ${if (remaining > 0) remaining else 0}${stringResource(R.string.ml)}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    if (showEditDialog) {
        NumberInputDialog(
            title = stringResource(R.string.daily_goal_setting_title),
            validators = listOf {
                (it in 0..50000).asValidationResult()
            },
            onDismiss = { value ->
                if (value != null) viewModel.setDailyGoal(value)
                showEditDialog = false
            }
        )
    }
}

@Composable
fun WaterStatusSelection(
    modifier: Modifier = Modifier,
    uiState: UIState,
    viewModel: WaterTrackerViewModel
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WaterStatusBlock(uiState = uiState)
        WaterRemainingBlock(uiState = uiState, viewModel = viewModel)
    }
}
