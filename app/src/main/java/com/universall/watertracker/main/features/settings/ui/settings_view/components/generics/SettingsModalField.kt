package com.universall.watertracker.main.features.settings.ui.settings_view.components.generics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SettingsModalField(
    modifier: Modifier = Modifier,
    title: String,
    currentValue: String?,
    icon: ImageVector,
    modal: @Composable (onClose: () -> Unit) -> Unit
) {
    var modalOpened by remember { mutableStateOf(false) }

    SettingsValueField(
        modifier = modifier,
        title = title,
        value = currentValue,
        icon = icon,
        onClick = { modalOpened = true }
    )

    if (modalOpened) {
        modal { modalOpened = false }
    }
}