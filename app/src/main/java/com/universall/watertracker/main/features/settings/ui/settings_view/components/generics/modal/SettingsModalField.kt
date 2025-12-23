package com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.modal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.GenericSettingsField

@Composable
fun SettingsModalField(
    modifier: Modifier = Modifier,
    title: String,
    currentValue: String?,
    icon: ImageVector,
    modal: @Composable (onClose: () -> Unit) -> Unit
) {
    var modalOpened by remember { mutableStateOf(false) }

    GenericSettingsField(
        modifier = modifier,
        title = title,
        currentValue = currentValue,
        icon = icon,
        onClick = { modalOpened = true }
    )

    if (modalOpened) {
        modal { modalOpened = false }
    }
}