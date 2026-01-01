package com.universall.watertracker.main.features.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.universall.watertracker.core.SingletonHolder2
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class NotificationsSchedulerImplST private constructor(
    private val context: Context,
    private val settingsService: SettingsService
) : NotificationsScheduler {
    private val workName = "notifications_work"
    private val scope = CoroutineScope(context = SupervisorJob() + Dispatchers.Default)

    override fun scheduleNotificationsWork(
        interval: Long
    ) {
        val work = PeriodicWorkRequestBuilder<NotificationWorker>(interval, TimeUnit.MINUTES).build()

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
                    scheduleNotificationsWork(interval = settings.reminderInterval.toLong())
                } else {
                    cancelNotificationsWork()
                }
            }
        }
    }

    companion object : SingletonHolder2<NotificationsSchedulerImplST, Context, SettingsService>(::NotificationsSchedulerImplST)
}
