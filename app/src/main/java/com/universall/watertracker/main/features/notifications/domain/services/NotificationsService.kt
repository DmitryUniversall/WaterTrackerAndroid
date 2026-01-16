package com.universall.watertracker.main.features.notifications.domain.services

import android.content.Context
import com.universall.watertracker.core.TimeRange
import java.time.LocalDateTime

interface NotificationsService {
    fun calculateNextNotificationDateTime(
        interval: Int,
        range: TimeRange?,
        now: LocalDateTime = LocalDateTime.now()
    ): LocalDateTime

    suspend fun calculateNextNotificationDateTimeFromSettings(): LocalDateTime

    fun isAllowedToSendNotifications(context: Context): Boolean

    suspend fun setupNotifications(context: Context)

    suspend fun subscribeToNotificationSent(onNotificationSent: suspend () -> Unit)
}
