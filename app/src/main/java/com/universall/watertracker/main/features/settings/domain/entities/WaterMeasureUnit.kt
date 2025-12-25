package com.universall.watertracker.main.features.settings.domain.entities

import com.universall.watertracker.core.HasId
import com.universall.watertracker.core.ui.Displayable

enum class WaterMeasureUnit(
    override val id: Int,
    override val title: String,
    val titleShort: String
) : Displayable, HasId {

    ML(1, "Milliliters (ml)", "ml");
}
