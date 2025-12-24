package com.universall.watertracker.core.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun GenericDialog(
    title: String,
    isOkEnabled: Boolean = true,
    isCancelEnabled: Boolean = true,
    buttonOkText: String? = "ok",
    buttonCancelText: String? = "cancel",
    onDismiss: () -> Unit = {},
    onOk: () -> Unit = {},
    onCancel: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.surface, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
                .testTag("generic_dialog"),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                // Content
                content()

                // Action button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (buttonCancelText != null && isCancelEnabled) {
                        TextButton(
                            modifier = Modifier
                                .testTag("generic_dialog_cancel_button"),
                            onClick = onCancel
                        ) {
                            Text(
                                text = buttonCancelText,
                                style = MaterialTheme.typography.titleMedium,
                                color = colors.primary
                            )
                        }
                    }

                    if (buttonOkText != null && isOkEnabled) {
                        TextButton(
                            modifier = Modifier
                                .testTag("generic_dialog_ok_button"),
                            onClick = onOk
                        ) {
                            Text(
                                text = buttonOkText,
                                style = MaterialTheme.typography.titleMedium,
                                color = colors.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
