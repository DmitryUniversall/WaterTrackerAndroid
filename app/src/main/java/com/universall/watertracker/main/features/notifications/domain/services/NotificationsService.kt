package com.universall.watertracker.main.features.notifications.domain.services

import com.universall.watertracker.main.features.notifications.domain.entities.ScheduledNotificationData
import kotlinx.coroutines.flow.Flow

interface NotificationsService {
    val scheduledNotificationDataFlow: Flow<ScheduledNotificationData?>

    fun launchNotificationsWork()
    fun isAllowedToSendNotifications(): Boolean
}
