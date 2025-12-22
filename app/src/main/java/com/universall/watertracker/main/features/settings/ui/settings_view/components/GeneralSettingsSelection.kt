package com.universall.watertracker.main.features.settings.ui.settings_view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Bold
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.bold.CaretRight
import com.adamglin.phosphoricons.regular.Drop

@Composable
fun GenericSettingsField(  // TODO: Make universal modal-caller field
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(12.dp)
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = PhosphorIcons.Regular.Drop,
                contentDescription = null,
                tint = colors.primary
            )

            Text(
                text = "Daily goal",
                color = colors.onSurface,
                style = typography.labelLarge
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "2.5 L",
                color = colors.primary,
                style = typography.labelLarge.copy(fontWeight = FontWeight.Normal)
            )

            Icon(
                modifier = Modifier.size(12.dp),
                imageVector = PhosphorIcons.Bold.CaretRight,
                contentDescription = null,
                tint = colors.secondary
            )
        }
    }
}


@Composable
fun GeneralSettingsSelection() {
    val colors = MaterialTheme.colorScheme

    SettingsSelection(
        title = "General"
    ) {
        GenericSettingsField(
            contentPadding = PaddingValues(
                start = 12.dp,
                end = 12.dp,
                top = 0.dp,
                bottom = 12.dp
            )
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = colors.onSurfaceVariant.copy(alpha = 0.7f)
        )

        GenericSettingsField()
        HorizontalDivider(
            thickness = 1.dp,
            color = colors.onSurfaceVariant.copy(alpha = 0.7f)
        )

        GenericSettingsField()
        HorizontalDivider(
            thickness = 1.dp,
            color = colors.onSurfaceVariant.copy(alpha = 0.7f)
        )

        GenericSettingsField(
            contentPadding = PaddingValues(
                start = 12.dp,
                end = 12.dp,
                top = 12.dp,
                bottom = 0.dp
            )
        )
    }
}