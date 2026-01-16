package com.universall.watertracker.main.features.notifications.data.internal_utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.universall.watertracker.main.features.notifications.data.repositories.NotificationsRepositoryImplST
import com.universall.watertracker.main.features.notifications.domain.repositories.NotificationsRepository
import kotlinx.coroutines.runBlocking

class NotificationReceiver : BroadcastReceiver() {
    val repository: NotificationsRepository = NotificationsRepositoryImplST.get()

    override fun onReceive(context: Context, intent: Intent?) {
        Log.i("WaterTrackerNotifications", "Getting ready to send scheduled notification")

        val enabled = runBlocking {
            repository.isNotificationsEnabledAndAllowed(context)
        }

        if (!enabled) {
            Log.i("WaterTrackerNotifications", "Cancelled scheduled notification: notifications disabled")

            runBlocking { repository.cancelNotifications(context) }
            return
        }

        showNotification(context)
        runBlocking { repository.scheduleNextNotification(context) }
    }

    private fun showNotification(context: Context) {
        Log.i("WaterTrackerNotifications", "Sending scheduled notification")

        val notification = repository.createReminderNotification(context)
        repository.sendNotification(context, notification)
    }
}
