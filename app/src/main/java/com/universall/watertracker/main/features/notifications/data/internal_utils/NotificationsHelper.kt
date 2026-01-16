package com.universall.watertracker.main.features.notifications.data.internal_utils

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
import com.universall.watertracker.core.TimeRange
import com.universall.watertracker.core.isAfterOrEqual
import com.universall.watertracker.core.isBeforeOrEqual
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.runBlocking
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

object NotificationsHelper {
    const val CHANNEL_ID = "water_intake_notifications"
    const val CHANNEL_NAME = "Water intake notifications"
    const val NOTIFICATION_ID = 1001

    private val _notificationSentNotifier = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1)
    val notificationSentNotifier: Flow<Unit> = _notificationSentNotifier.asSharedFlow()

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

    fun buildNotification(context: Context, title: String, message: String): Notification {  // TODO: Set sound
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun sendNotification(context: Context, notification: Notification) {
        if (!isAllowedToSendNotifications(context)) {
            Log.w("WaterTrackerNotifications", "Unable to send notification: Permission denied")
            return
        }

        createChannel(context)

        val manager = NotificationManagerCompat.from(context)
        manager.notify(NOTIFICATION_ID, notification)

        runBlocking { _notificationSentNotifier.emit(Unit) }
    }

    fun isPermissionGranted(context: Context): Boolean {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU || ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    }

    fun isSystemNotificationsEnabled(context: Context): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    fun isAllowedToSendNotifications(context: Context): Boolean {
        return isPermissionGranted(context) && isSystemNotificationsEnabled(context)
    }

    fun calculateNextNotificationDateTime(
        interval: Int,
        range: TimeRange?,
        now: LocalDateTime = LocalDateTime.now()
    ): LocalDateTime {
        if (range == null) return now.plusMinutes(interval.toLong())

        fun rangeStartForDate(date: LocalDate): LocalDateTime {
            return LocalDateTime.of(date, range.start)
        }

        fun rangeEndForDate(startDate: LocalDate): LocalDateTime {
            val endDate = if (range.end.isAfter(range.start)) startDate else startDate.plusDays(1)
            return LocalDateTime.of(endDate, range.end)
        }

        val today = now.toLocalDate()

        val windowEnd = rangeEndForDate(today)
        if (now.isAfterOrEqual(windowEnd)) return rangeStartForDate(today.plusDays(1))

        val windowStart = rangeStartForDate(today)
        if (now.isBefore(windowStart)) return windowStart

        val minutesSinceStart = Duration.between(windowStart, now).toMinutes()
        val steps = (minutesSinceStart / interval).toInt() + 1
        val candidate = windowStart.plusMinutes(steps.toLong() * interval)

        return if (candidate.isBeforeOrEqual(windowEnd)) candidate else rangeStartForDate(today.plusDays(1))
    }
}
