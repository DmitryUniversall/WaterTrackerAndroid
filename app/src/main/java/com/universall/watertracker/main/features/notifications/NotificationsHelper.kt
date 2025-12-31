package com.universall.watertracker.main.features.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.universall.watertracker.R
import java.util.concurrent.TimeUnit

object NotificationsHelper {
    private const val WORK_NAME = "notifications_work"

    const val CHANNEL_ID = "water_intake_notifications"
    const val CHANNEL_NAME = "water_intake_notifications"
    const val NOTIFICATION_ID = 1001

    fun createChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Water intake notifications"
        }

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    fun buildNotification(context: Context, title: String, message: String): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build()
    }

    fun scheduleNotificationsWork(
        context: Context,
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

    fun cancelNotificationsWork(context: Context) {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelUniqueWork(WORK_NAME)
    }
}
