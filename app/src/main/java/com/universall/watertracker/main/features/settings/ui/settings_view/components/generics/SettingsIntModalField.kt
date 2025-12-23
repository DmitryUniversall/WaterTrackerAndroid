package com.universall.watertracker.main.features.settings.ui.settings_view.components.generics

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.universall.watertracker.core.Validator
import com.universall.watertracker.core.ui.dialogs.NumberInputDialog
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.modal.SettingsModalField

@Composable
fun SettingsIntModalField(
    // Field params
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(12.dp),
    title: String,
    currentValue: String?,
    icon: ImageVector,

    // Modal params
    modalTitle: String? = null,
    inputLabel: String?  = null,
    onDismiss: (Int?) -> Unit,
    validators: List<Validator<Int>> = emptyList()
) {
    SettingsModalField(
        modifier = modifier,
        title = title,
        currentValue = currentValue,
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
