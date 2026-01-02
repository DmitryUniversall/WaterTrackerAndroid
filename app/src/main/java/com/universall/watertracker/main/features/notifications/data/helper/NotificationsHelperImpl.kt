package com.universall.watertracker.main.features.notifications.data.helper

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.universall.watertracker.R

class NotificationsHelperImpl(
    private val context: Context
) : NotificationsHelper {
    override val channelId = "water_intake_notifications"
    override val channelName = "water_intake_notifications"
    override val notificationId = 1001

    override fun createChannel() {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Water intake notifications"
        }

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    override fun buildNotification(title: String, message: String): Notification {
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun sendNotification(notification: Notification) {
        if (!isAllowedToSendNotifications()) {
            Log.w("WaterTrackerNotificationWorker", "Unable to send notification: Permission denied")
            return
        }

        createChannel()

        val manager = NotificationManagerCompat.from(context)
        manager.notify(notificationId, notification)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun sendNotification(title: String, message: String) {
        val notification = buildNotification(title, message)
        sendNotification(notification)
    }

    override fun isPermissionGranted(): Boolean {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU || ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    }

    override fun isSystemNotificationsEnabled(): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    override fun isAllowedToSendNotifications(): Boolean {
        return isPermissionGranted() && isSystemNotificationsEnabled()
    }
}
