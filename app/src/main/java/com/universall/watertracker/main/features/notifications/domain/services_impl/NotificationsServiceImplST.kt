package com.universall.watertracker.main.features.notifications.domain.services_impl

import com.universall.watertracker.core.SingletonHolder
import com.universall.watertracker.main.features.notifications.domain.entities.ScheduledNotificationData
import com.universall.watertracker.main.features.notifications.domain.repositories.NotificationsRepository
import com.universall.watertracker.main.features.notifications.domain.services.NotificationsService
import kotlinx.coroutines.flow.Flow

class NotificationsServiceImplST(
    private val repository: NotificationsRepository,
) : NotificationsService {
    override val scheduledNotificationDataFlow: Flow<ScheduledNotificationData?> = repository.scheduledNotificationDataFlow

    override fun launchNotificationsWork() {
        repository.launchNotificationsController()
    }

    override fun isAllowedToSendNotifications(): Boolean {
        return repository.isAllowedToSendNotifications()
    }

    companion object : SingletonHolder<NotificationsServiceImplST, NotificationsRepository>(::NotificationsServiceImplST)
}
