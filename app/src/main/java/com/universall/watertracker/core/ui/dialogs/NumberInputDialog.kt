package com.universall.watertracker.core.ui.dialogs

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.universall.watertracker.core.Validator

@Composable
fun NumberInputDialog(
    title: String,
    inputLabel: String?,
    onDismiss: (Int?) -> Unit,
    validators: List<Validator<Int>> = emptyList()
) {
    InputDialog(
        title = title,
        inputLabel = inputLabel,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

        onDismiss = onDismiss,
        validators = validators,
        valueConverter = { old, input ->
            if (!input.all { it.isDigit() }) old
            else input.toIntOrNull()
        },
    )
}
