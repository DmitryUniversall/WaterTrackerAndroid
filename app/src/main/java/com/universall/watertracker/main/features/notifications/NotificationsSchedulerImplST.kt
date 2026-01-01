package com.universall.watertracker.main.features.notifications

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.universall.watertracker.R
import com.universall.watertracker.core.SingletonHolder3
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class NotificationsSchedulerImplST private constructor(
    private val context: Context,
    private val settingsService: SettingsService,
    private val notificationsHelper: NotificationsHelper
) : NotificationsScheduler {
    private val workName = "notifications_work"
    private val scope = CoroutineScope(context = SupervisorJob() + Dispatchers.Default)

    init {
        scope.launch {
            val settingsState = settingsService.settingsFlow.first()  // Do it only 1 time on program start
            Log.i("App", "Allowed: ${notificationsHelper.isAllowedToSendNotifications()}")
            if (settingsState.notificationsEnabled && !notificationsHelper.isAllowedToSendNotifications()) {
                settingsService.setNotificationsEnabled(false)
            }
        }
    }

    override fun scheduleNotificationsWork(
        interval: Long,
        title: String,
        message: String
    ) {
        val date = workDataOf(
            NotificationWorkerDataKeys.TITLE to title,
            NotificationWorkerDataKeys.MESSAGE to message
        )

        val work = PeriodicWorkRequestBuilder<NotificationWorker>(
            repeatInterval = interval,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        ).setInputData(date).build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniquePeriodicWork(
            "interval_notifications",
            ExistingPeriodicWorkPolicy.REPLACE,
            work
        )
    }

    override fun cancelNotificationsWork() {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelUniqueWork(uniqueWorkName = workName)
    }

    override fun launch() {
        val helper = NotificationsHelperImplST.get()

        scope.launch {
            settingsService.settingsFlow.collect { settings ->
                if (settings.notificationsEnabled && helper.isAllowedToSendNotifications()) {
                    scheduleNotificationsWork(
                        interval = settings.reminderInterval.toLong(),
                        title = context.getString(R.string.notification_reminder_title),
                        message = context.getString(R.string.notification_reminder_message)
                    )
                } else {
                    cancelNotificationsWork()
                }
            }
        }
    }

    companion object : SingletonHolder3<NotificationsSchedulerImplST, Context, SettingsService, NotificationsHelper>(::NotificationsSchedulerImplST)
}
