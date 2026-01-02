package com.universall.watertracker.main.features.notifications.domain.entities

import java.time.LocalDateTime

data class ScheduledNotificationData(
    val sendAt: LocalDateTime,
    val title: String,
    val message: String
)
