package com.universall.watertracker.main.features.notifications.data.repositories

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.universall.watertracker.R
import com.universall.watertracker.core.SingletonHolder2
import com.universall.watertracker.core.TimeRange
import com.universall.watertracker.core.asTimestampWithDefaultZone
import com.universall.watertracker.core.background_tasks.WorkScheduler
import com.universall.watertracker.core.toLocalDateTime
import com.universall.watertracker.main.features.notifications.data.helper.NotificationsHelper
import com.universall.watertracker.main.features.notifications.data.helper.NotificationsHelperImpl
import com.universall.watertracker.main.features.notifications.data.preferences.NotificationsPreferences
import com.universall.watertracker.main.features.notifications.data.preferences.notificationsDataStore
import com.universall.watertracker.main.features.notifications.data.worker.NotificationWorker
import com.universall.watertracker.main.features.notifications.domain.entities.ScheduledNotificationData
import com.universall.watertracker.main.features.notifications.domain.repositories.NotificationsRepository
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime


class NotificationsRepositoryImplST(
    private val context: Context,
    private val settingsService: SettingsService
) : NotificationsRepository {
    private val dataStore = context.notificationsDataStore
    private val scope = CoroutineScope(context = SupervisorJob() + Dispatchers.Default)

    private val helper: NotificationsHelper = NotificationsHelperImpl(context)
    private val scheduler: WorkScheduler = WorkScheduler(context, "notifications_work")

    override val scheduledNotificationDataFlow: Flow<ScheduledNotificationData?> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences ->
            fromPrimitives(
                nextNotificationTimestamp = preferences[NotificationsPreferences.SCHEDULED_NOTIFICATION_TIMESTAMP],
                title = preferences[NotificationsPreferences.SCHEDULED_NOTIFICATION_TITLE],
                message = preferences[NotificationsPreferences.SCHEDULED_NOTIFICATION_MESSAGE],
            )
        }

    private fun fromPrimitives(
        nextNotificationTimestamp: Long?,
        title: String?,
        message: String?
    ): ScheduledNotificationData? {
        if (
            nextNotificationTimestamp == null ||
            title == null ||
            message == null
        ) return null

        return ScheduledNotificationData(
            sendAt = nextNotificationTimestamp.toLocalDateTime(),
            title = title,
            message = message
        )
    }

    private suspend fun deleteScheduledNotificationData() {
        dataStore.edit { preferences ->
            preferences.remove(NotificationsPreferences.SCHEDULED_NOTIFICATION_TIMESTAMP)
            preferences.remove(NotificationsPreferences.SCHEDULED_NOTIFICATION_TITLE)
            preferences.remove(NotificationsPreferences.SCHEDULED_NOTIFICATION_MESSAGE)
        }
    }

    private suspend fun writeScheduledNotificationData(data: ScheduledNotificationData) {
        dataStore.edit { preferences ->
            preferences[NotificationsPreferences.SCHEDULED_NOTIFICATION_TIMESTAMP] = data.sendAt.asTimestampWithDefaultZone()
            preferences[NotificationsPreferences.SCHEDULED_NOTIFICATION_TITLE] = data.title
            preferences[NotificationsPreferences.SCHEDULED_NOTIFICATION_MESSAGE] = data.message
        }
    }

    private fun computeNextReminderTime(
        reminderInterval: Int,
        reminderTimeRange: TimeRange? = null
    ): LocalDateTime {
        val now = LocalDateTime.now()
        val nextCandidate = now.plusMinutes(reminderInterval.toLong())

        val startTime: LocalTime? = reminderTimeRange?.start
        val endTime: LocalTime? = reminderTimeRange?.end
        val candidateTime = nextCandidate.toLocalTime()

        return if (reminderTimeRange == null || candidateTime in startTime!!..endTime!!) {
            nextCandidate
        } else {
            if (candidateTime < startTime)
                LocalDateTime.now().withHour(startTime.hour).withMinute(startTime.minute)
            else
                LocalDateTime.now().plusDays(1).withHour(startTime.hour).withMinute(startTime.minute)
        }
    }

    private fun sendNotification(title: String, message: String) {
        helper.sendNotification(title, message)
    }

    private suspend fun scheduleNotificationAt(sendNotificationAt: LocalDateTime, title: String, message: String) {
        Log.i("WaterTrackerNotifications", "Scheduling notification to: $sendNotificationAt")

        writeScheduledNotificationData(
            ScheduledNotificationData(
                sendAt = sendNotificationAt,
                title = title,
                message = message
            )
        )

        scheduler.runWorkerAt(
            worker = NotificationWorker::class,
            whenUtcMillis = sendNotificationAt.asTimestampWithDefaultZone()
        )
    }

    private suspend fun scheduleNextReminder(title: String, message: String) {
        val settings = settingsService.settingsFlow.first()
        scheduleNotificationAt(
            sendNotificationAt = computeNextReminderTime(settings.reminderInterval, settings.reminderTimeRange),
            title = title,
            message = message
        )
    }

    private suspend fun rescheduleNotificationIfNeeded(
        notificationsEnabled: Boolean,
        scheduledNotification: ScheduledNotificationData?,
        replaceWorker: Boolean = false
    ) {
        // Stop worker if all not allowed to send notifications
        if (!helper.isAllowedToSendNotifications()) {
            if (notificationsEnabled) {
                Log.w("WaterTrackerNotifications", "Disabling notifications because app doesn't have permission to post them")
                settingsService.setNotificationsEnabled(false)
            }

            if (scheduler.isWorkScheduled()) {  // TODO: Do i need this?
                Log.w("WaterTrackerNotifications", "App not allowed to send notifications, canceling work")
                cancelNotificationsWorker()
            }

            if (scheduledNotification != null) {
                Log.w("WaterTrackerNotifications", "App not allowed to send notifications, deleting scheduled notification data")
                deleteScheduledNotificationData()
            }

            return
        }

        // return if worker for scheduled notification is already running
        if (!replaceWorker && scheduler.isWorkScheduled()) {  // TODO: replaceWorker - hack
            Log.i("WaterTrackerNotifications", "Scheduled notification work is already created")
            return
        }

        // TODO: Check if current scheduled notification is in time range

        // Notification should be sent
        if (scheduledNotification != null) {
            Log.i("WaterTrackerNotifications", "Creating work for scheduled notification")

            // Notification is scheduled, but worker is not running, restore
            scheduleNotificationAt(
                sendNotificationAt = scheduledNotification.sendAt,
                title = context.getString(R.string.notification_reminder_title),
                message = context.getString(R.string.notification_reminder_message)
            )
        } else {
            Log.i("WaterTrackerNotifications", "Scheduling next notification")

            // Schedule next notification
            scheduleNextReminder(
                title = context.getString(R.string.notification_reminder_title),
                message = context.getString(R.string.notification_reminder_message)
            )
        }
    }

    override fun isAllowedToSendNotifications(): Boolean {
        return helper.isAllowedToSendNotifications()
    }

    override suspend fun isNotificationsEnabledAndAllowed(): Boolean {
        val settings = settingsService.settingsFlow.first()
        return settings.notificationsEnabled && isAllowedToSendNotifications()
    }

    override suspend fun sendScheduledNotification() {
        val scheduledNotification = scheduledNotificationDataFlow.first()
        val settings = settingsService.settingsFlow.first()

        if (scheduledNotification == null) {
            Log.w("WaterTrackerNotifications", "Called sendScheduledNotification, but no notification was scheduled. Aborting")
            return
        }

        if (settings.reminderTimeRange != null && LocalTime.now() !in settings.reminderTimeRange.start..settings.reminderTimeRange.end) {
            Log.w("WaterTrackerNotifications", "Abort sending notification: Not in time range")
            deleteScheduledNotificationData()
            return
        }

        if (scheduledNotification.sendAt > LocalDateTime.now()) {
            Log.w("WaterTrackerNotifications", "Sending scheduled notification earlier than planned")
        }

        Log.i("WaterTrackerNotifications", "Sending scheduled notification")
        sendNotification(scheduledNotification.title, scheduledNotification.message)
        deleteScheduledNotificationData()
    }

    override fun cancelNotificationsWorker() {
        Log.i("WaterTrackerNotifications", "Cancelling notifications work")
        scheduler.cancelWorker()
    }

    override fun launchNotificationsController() {  // FIXME: Works only when app isn't close in task manager
        scope.launch {
            combine(
                settingsService.settingsFlow.distinctUntilChanged(),
                scheduledNotificationDataFlow
            ) { settings, notificationsState ->
                Pair(
                    settings.notificationsEnabled,
                    notificationsState,
                )
            }.collect { (notificationsEnabled, scheduledNotification) ->
                rescheduleNotificationIfNeeded(  // FIXME: Called twice: when notification is deleted and when new notification is created
                    notificationsEnabled = notificationsEnabled,
                    scheduledNotification = scheduledNotification,
                    replaceWorker = true
                )
            }
        }
    }

    companion object : SingletonHolder2<NotificationsRepositoryImplST, Context, SettingsService>(::NotificationsRepositoryImplST)
}
