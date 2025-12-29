package com.universall.watertracker.main.features.settings.ui.settings_view.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.DropSimple
import com.adamglin.phosphoricons.regular.Plus
import com.adamglin.phosphoricons.regular.PlusCircle
import com.universall.watertracker.R
import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit
import com.universall.watertracker.main.features.settings.ui.settings_view.SettingsViewModel
import com.universall.watertracker.main.features.settings.ui.settings_view.components.generics.SettingsIntModalField

@Composable
fun MainScreenSettingsSelection(
    viewModel: SettingsViewModel,
    addButtonValue: Int,
    waterMeasureUnit: WaterMeasureUnit
) {
    SettingsSelection(
        title = stringResource(R.string.main_screen_settings_selection),
        containerPadding = PaddingValues(24.dp)
    ) {
        SettingsIntModalField(
            modifier = Modifier
                .height(30.dp),
            title = stringResource(R.string.add_button_value_setting_title),
            displayValue = "$addButtonValue ${waterMeasureUnit.titleShort}",
            icon = PhosphorIcons.Regular.PlusCircle,

            onDismiss = { value ->
                if (value != null) viewModel.setAddButtonValue(value)
            },
        )
    }
}
