package com.universall.watertracker.main.features.water_tracker.ui.water_view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.universall.watertracker.R
import com.universall.watertracker.core.ui.SkeletonBox
import com.universall.watertracker.main.features.water_tracker.ui.water_view.UIState


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
                    text = uiState.waterAmount.toString(),
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
    uiState: UIState
) {
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
            val remaining: Int = 1200 - uiState.waterAmount

            Text(
                text = "${stringResource(R.string.remaining)} ${if(remaining > 0) remaining else 0}${stringResource(R.string.ml)}",  // TODO: 1200 -> waterAmountGoal
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun WaterStatusSelection(
    modifier: Modifier = Modifier,
    uiState: UIState
) {
    Column(
        modifier = modifier
            .padding(top = 64.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WaterStatusBlock(uiState = uiState)
        WaterRemainingBlock(uiState = uiState)
    }
}
