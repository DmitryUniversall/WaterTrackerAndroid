package com.universall.watertracker.main.features.settings.ui.settings_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SettingsSelection(
    title: String,
    containerModifier: Modifier = Modifier,
    containerPadding: PaddingValues = PaddingValues(24.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 12.dp),
            text = title,
            color = colors.secondary,
            style = typography.labelLarge
        )

        Column(
            modifier = Modifier
                .background(
                    color = colors.surface,
                    shape = RoundedCornerShape(24.dp)
                )
                .clip(shape = RoundedCornerShape(24.dp))
        ) {
            Column(
                modifier = containerModifier
                    .padding(containerPadding)
            ) {
                content()
            }
        }
    }
}
