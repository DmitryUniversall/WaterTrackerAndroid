package com.universall.watertracker.main.features.notifications.data.repositories

import android.Manifest
import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import com.universall.watertracker.R
import com.universall.watertracker.core.SingletonHolder
import com.universall.watertracker.core.TimeRange
import com.universall.watertracker.main.features.notifications.data.internal_utils.NotificationScheduler
import com.universall.watertracker.main.features.notifications.data.internal_utils.NotificationsHelper
import com.universall.watertracker.main.features.notifications.domain.repositories.NotificationsRepository
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime


class NotificationsRepositoryImplST(
    private val settingsService: SettingsService
) : NotificationsRepository {
    private val helper: NotificationsHelper = NotificationsHelper
    private val scheduler: NotificationScheduler = NotificationScheduler

    override fun createNotificationsChannel(context: Context) {
        helper.createChannel(context)
    }

    override fun createReminderNotification(context: Context): Notification {
        return helper.buildNotification(
            context = context,
            title = context.getString(R.string.notification_reminder_title),
            message = context.getString(R.string.notification_reminder_message)
        )
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun sendNotification(context: Context, notification: Notification) {
        helper.sendNotification(context, notification)
    }

    override suspend fun scheduleNextNotification(context: Context) {
        Log.i("WaterTrackerNotifications", "Scheduling next notification")

        val settings = settingsService.settingsFlow.first()

        val nextNotificationDatetime = helper.calculateNextNotificationDateTime(
            interval = settings.reminderInterval,
            range = settings.reminderTimeRange
        )

        scheduler.scheduleNext(context, nextNotificationDatetime)
    }

    override suspend fun rescheduleNotifications(context: Context) {
        Log.i("WaterTrackerNotifications", "Rescheduling notifications")

        cancelNotifications(context)
        scheduleNextNotification(context)
    }

    override suspend fun cancelNotifications(context: Context) {
        Log.i("WaterTrackerNotifications", "Cancelling notifications")

        scheduler.cancel(context)
    }

    override fun isAllowedToSendNotifications(context: Context): Boolean {
        return helper.isAllowedToSendNotifications(context) && scheduler.hasPermissionToSchedule(context)
    }

    override suspend fun isNotificationsEnabledAndAllowed(context: Context): Boolean {
        val settings = settingsService.settingsFlow.first()
        return settings.notificationsEnabled && isAllowedToSendNotifications(context)
    }

    override fun calculateNextNotificationDateTime(interval: Int, range: TimeRange?, now: LocalDateTime): LocalDateTime {
        return helper.calculateNextNotificationDateTime(
            interval = interval,
            range = range,
            now = now
        )
    }

    override suspend fun calculateNextNotificationDateTimeFromSettings(): LocalDateTime {
        val settings = settingsService.settingsFlow.first()

        return calculateNextNotificationDateTime(
            interval = settings.reminderInterval,
            range = settings.reminderTimeRange
        )
    }

    override suspend fun subscribeToNotificationSent(onNotificationSent: suspend () -> Unit) {
        helper.notificationSentNotifier.collect { onNotificationSent() }
    }

    companion object : SingletonHolder<NotificationsRepositoryImplST, SettingsService>(::NotificationsRepositoryImplST)
}
