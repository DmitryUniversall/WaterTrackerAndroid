package com.universall.watertracker.main.features.notifications.domain.repositories

import com.universall.watertracker.main.features.notifications.domain.entities.ScheduledNotificationData
import kotlinx.coroutines.flow.Flow

interface NotificationsRepository {
    val scheduledNotificationDataFlow: Flow<ScheduledNotificationData?>

    fun launchNotificationsController()
    fun cancelNotificationsWorker()

    fun isAllowedToSendNotifications(): Boolean
    suspend fun isNotificationsEnabledAndAllowed(): Boolean

    suspend fun sendScheduledNotification()
}
