package com.universall.watertracker.main.features.settings.domain.entities

import com.universall.watertracker.core.ui.Displayable

enum class NotificationSound(override val title: String) : Displayable {
    FRESH("FRESH"),
    HAPPY("HAPPY"),
    LITTLE("LITTLE"),
    EMERGENCE("EMERGENCE"),
    ECHO("ECHO"),
    DING("DING")
}
