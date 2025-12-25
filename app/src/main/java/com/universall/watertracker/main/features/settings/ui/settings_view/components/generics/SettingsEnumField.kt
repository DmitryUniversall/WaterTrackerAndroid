package com.universall.watertracker.main.features.settings.ui.settings_view.components.generics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.universall.watertracker.core.ui.Displayable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SettingsEnumField(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,

    value: T?,
    options: Array<T>,
    onValueSelected: (T) -> Unit
) where T : Enum<T>, T : Displayable {
    var expanded by remember { mutableStateOf(false) }
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    GenericSettingsField(
        modifier = modifier,
        title = title,
        icon = icon
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            Text(
                modifier = Modifier
                    .menuAnchor(
                        type = ExposedDropdownMenuAnchorType.PrimaryEditable,
                        enabled = true
                    ),
                text = value?.title ?: "",
                color = colors.primary,
                style = typography.labelLarge.copy(fontWeight = FontWeight.Normal)
            )

            ExposedDropdownMenu(
                modifier = Modifier
                    .background(color = colors.surface)
                    .width(IntrinsicSize.Min),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.title) },
                        onClick = {
                            onValueSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
