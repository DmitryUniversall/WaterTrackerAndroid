package com.universall.watertracker.main.features.notifications.domain.repositories

import android.app.Notification
import android.content.Context
import com.universall.watertracker.core.TimeRange
import java.time.LocalDateTime

interface NotificationsRepository {
    fun createNotificationsChannel(context: Context)

    fun createReminderNotification(context: Context): Notification
    fun sendNotification(context: Context, notification: Notification)

    suspend fun cancelNotifications(context: Context)
    suspend fun scheduleNextNotification(context: Context)
    suspend fun rescheduleNotifications(context: Context)

    fun isAllowedToSendNotifications(context: Context): Boolean
    suspend fun isNotificationsEnabledAndAllowed(context: Context): Boolean

    fun calculateNextNotificationDateTime(
        interval: Int,
        range: TimeRange?,
        now: LocalDateTime = LocalDateTime.now()
    ): LocalDateTime

    suspend fun calculateNextNotificationDateTimeFromSettings(): LocalDateTime

    suspend fun subscribeToNotificationSent(onNotificationSent: suspend () -> Unit)
}
