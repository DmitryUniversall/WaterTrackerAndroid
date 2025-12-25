package com.universall.watertracker.main.features.settings.domain.entities

import com.universall.watertracker.core.HasId
import com.universall.watertracker.core.ui.Displayable

enum class NotificationSound(
    override val id: Int,
    override val title: String
) : Displayable, HasId {
    FRESH(1, "FRESH"),
    HAPPY(2, "HAPPY"),
    LITTLE(3, "LITTLE"),
    EMERGENCE(4, "EMERGENCE"),
    ECHO(5, "ECHO"),
    DING(6, "DING")
}
