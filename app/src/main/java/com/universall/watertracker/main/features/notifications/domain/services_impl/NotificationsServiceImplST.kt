package com.universall.watertracker.main.features.notifications.domain.services_impl

import android.content.Context
import com.universall.watertracker.core.SingletonHolder2
import com.universall.watertracker.core.TimeRange
import com.universall.watertracker.main.features.notifications.domain.repositories.NotificationsRepository
import com.universall.watertracker.main.features.notifications.domain.services.NotificationsService
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class NotificationsServiceImplST(
    private val repository: NotificationsRepository,
    private val settingsService: SettingsService
) : NotificationsService {
    override fun calculateNextNotificationDateTime(interval: Int, range: TimeRange?, now: LocalDateTime): LocalDateTime {
        return repository.calculateNextNotificationDateTime(interval = interval, range = range, now = now)
    }

    override suspend fun calculateNextNotificationDateTimeFromSettings(): LocalDateTime {
        return repository.calculateNextNotificationDateTimeFromSettings()
    }

    override suspend fun setupNotifications(context: Context) {
        repository.createNotificationsChannel(context)
        scheduleNotificationsIfNeeded(context)
    }

    private suspend fun scheduleNotificationsIfNeeded(context: Context) {
        settingsService.settingsFlow
            .map { it.notificationsEnabled to it.reminderInterval }
            .distinctUntilChanged()
            .collect { pair ->
                if (!pair.first) {
                    repository.cancelNotifications(context)
                } else {
                    repository.rescheduleNotifications(context)
                }
            }

        if (repository.isNotificationsEnabledAndAllowed(context)) {
            repository.scheduleNextNotification(context)
        } else {
            repository.cancelNotifications(context)
        }
    }

    override fun isAllowedToSendNotifications(context: Context): Boolean {
        return repository.isAllowedToSendNotifications(context)
    }

    override suspend fun subscribeToNotificationSent(onNotificationSent: suspend () -> Unit) {
        return repository.subscribeToNotificationSent(onNotificationSent)
    }

    companion object : SingletonHolder2<NotificationsServiceImplST, NotificationsRepository, SettingsService>(::NotificationsServiceImplST)
}
