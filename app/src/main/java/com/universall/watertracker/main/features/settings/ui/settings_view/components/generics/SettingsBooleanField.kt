package com.universall.watertracker.main.features.settings.ui.settings_view.components.generics

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.universall.watertracker.core.ui.AppGenericMaterialSwitch

@Composable
fun SettingsBooleanField(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    value: Boolean,
    onSwitch: (checked: Boolean) -> Unit
) {
    GenericSettingsField(
        modifier = modifier,
        title = title,
        icon = icon
    ) {
        AppGenericMaterialSwitch(
            modifier = Modifier.fillMaxHeight(),
            checked = value,
            onCheckedChange = { onSwitch(it) }
        )
    }
}
