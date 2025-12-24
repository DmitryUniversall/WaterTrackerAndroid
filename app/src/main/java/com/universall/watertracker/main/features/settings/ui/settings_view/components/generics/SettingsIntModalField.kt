package com.universall.watertracker.main.features.settings.ui.settings_view.components.generics

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.universall.watertracker.core.Validator
import com.universall.watertracker.core.ui.dialogs.NumberInputDialog

@Composable
fun SettingsIntModalField(
    // Field params
    modifier: Modifier = Modifier,
    title: String,
    displayValue: String?,
    icon: ImageVector,

    // Modal params
    modalTitle: String? = null,
    inputLabel: String? = null,
    onDismiss: (Int?) -> Unit,
    validators: List<Validator<Int>> = emptyList()
) {
    SettingsModalField(
        modifier = modifier,
        title = title,
        displayValue = displayValue,
        icon = icon
    ) { onClose ->
        NumberInputDialog(
            title = modalTitle ?: title,
            inputLabel = inputLabel ?: title,
            validators = validators,
            onDismiss = { value ->
                onClose()
                onDismiss(value)
            }
        )
    }
}
