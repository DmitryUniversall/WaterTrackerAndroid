package com.universall.watertracker.core.ui.dialogs

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.universall.watertracker.R
import com.universall.watertracker.core.Validator

@Composable
fun <T> InputDialog(
    title: String,
    inputLabel: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,

    valueConverter: (T?, String) -> T?,
    validators: List<Validator<T>> = emptyList(),

    onDismiss: (T?) -> Unit
) {
    var currentValue by remember { mutableStateOf<T?>(null) }

    val validationError = currentValue?.let { value ->
        validators.map { it(value) }.firstOrNull { !it.isValid }
    }

    GenericDialog(
        title = title,
        onOk = { onDismiss(currentValue) },
        onDismiss = { onDismiss(null) },
        onCancel = { onDismiss(null) },
        buttonCancelText = stringResource(R.string.cancel),
        buttonOkText = stringResource(R.string.ok)
    ) {
        OutlinedTextField(
            modifier = Modifier.testTag("generic_input_dialog_field"),
            label = { Text(text = inputLabel ?: "") },
            keyboardOptions = keyboardOptions,

            isError = validationError != null,
            supportingText = { validationError?.error?.let { Text(text = it) } },

            value = currentValue?.toString().orEmpty(),
            onValueChange = { input: String ->
                currentValue = valueConverter(currentValue, input)
            }
        )
    }
}
