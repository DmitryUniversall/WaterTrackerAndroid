package com.universall.watertracker.main.features.settings.ui.settings_view.components.generics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Bold
import com.adamglin.phosphoricons.bold.CaretRight

@Composable
fun SettingsValueField(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    displayValue: String?,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = modifier
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GenericSettingsField(
            title = title,
            icon = icon
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (displayValue != null) {
                    Text(
                        text = displayValue,
                        color = colors.primary,
                        style = typography.labelLarge.copy(fontWeight = FontWeight.Normal)
                    )
                }

                Icon(
                    modifier = Modifier.size(12.dp),
                    imageVector = PhosphorIcons.Bold.CaretRight,
                    contentDescription = null,
                    tint = colors.secondary
                )
            }
        }
    }
}
