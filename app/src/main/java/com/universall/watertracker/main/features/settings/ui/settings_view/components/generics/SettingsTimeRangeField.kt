package com.universall.watertracker.main.features.settings.ui.settings_view.components.generics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.universall.watertracker.R
import com.universall.watertracker.core.TimeRange
import com.universall.watertracker.core.toHHMM
import com.universall.watertracker.core.ui.dialogs.GenericDialog
import com.universall.watertracker.core.ui.dialogs.TimePickerDialogComposable
import java.time.LocalTime


private enum class RangePickTarget {
    START, END
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTimeRangeField(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    dialogTitle: String? = null,

    value: TimeRange?,
    onDismiss: (TimeRange?) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    var selecting by remember { mutableStateOf<RangePickTarget?>(null) }

    var start by remember { mutableStateOf(value?.start) }
    var end by remember { mutableStateOf(value?.end) }

    fun emitValidOrNull() {
        if (start == null || end == null || end!!.isBefore(start!!)) {
            onDismiss(null); return
        }

        onDismiss(TimeRange(start!!, end!!))
    }

    if (selecting != null) {
        TimePickerDialogComposable(
            title = if (selecting == RangePickTarget.START) stringResource(R.string.start_time) else stringResource(
                R.string.end_time
            ),
            initial = (if (selecting == RangePickTarget.START) start else end) ?: LocalTime.now(),
            onDismiss = { selecting = null },
            onConfirm = { time ->
                if (selecting == RangePickTarget.START) start = time else end = time
                selecting = null
            }
        )
    }

    SettingsModalField(
        modifier = modifier,
        title = title,
        icon = icon,
        displayValue = value?.let { "${value.start.toHHMM()} - ${value.end.toHHMM()}" } ?: stringResource(R.string.not_specified)
    ) { onClose ->
        GenericDialog(
            title = dialogTitle ?: title,
            isOkEnabled = true,
            isCancelEnabled = true,
            onDismiss = { onClose(); onDismiss(null) },
            onCancel = { onClose(); onDismiss(null) },
            onOk = { onClose(); emitValidOrNull() }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .clickable(
                            onClick = { selecting = RangePickTarget.START }
                        ),
                    text = start.toHHMM(),
                    color = colors.onSurface,
                    style = typography.headlineLarge.copy(fontWeight = FontWeight.Normal)
                )

                HorizontalDivider(
                    modifier = Modifier
                        .width(48.dp)
                        .padding(horizontal = 12.dp),
                    thickness = 2.dp,
                    color = colors.onSurfaceVariant
                )

                Text(
                    modifier = Modifier
                        .clickable(
                            onClick = { selecting = RangePickTarget.END }
                        ),
                    text = end.toHHMM(),
                    color = colors.onSurface,
                    style = typography.headlineLarge.copy(fontWeight = FontWeight.Normal)
                )
            }
        }
    }
}
