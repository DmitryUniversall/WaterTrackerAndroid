package com.universall.watertracker.main.features.settings.ui.settings_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.universall.watertracker.main.common.ui.GenericScrollablePage
import com.universall.watertracker.main.features.settings.ui.settings_view.components.GeneralSettingsSelection
import com.universall.watertracker.main.features.settings.ui.settings_view.components.NotificationsSettingsSelection

@Composable
fun SettingsView(
    layoutPadding: PaddingValues
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    GenericScrollablePage(layoutPadding = layoutPadding) {
        Text(
            modifier = Modifier
                .padding(32.dp),
            text = "Settings",
            color = colors.onBackground,
            style = typography.headlineLarge
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GeneralSettingsSelection()
            NotificationsSettingsSelection()
        }
    }
}
