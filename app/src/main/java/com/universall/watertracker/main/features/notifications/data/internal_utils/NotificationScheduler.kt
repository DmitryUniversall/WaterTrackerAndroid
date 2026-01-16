package com.universall.watertracker.main.features.notifications.data.internal_utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.universall.watertracker.core.asTimestampWithDefaultZone
import com.universall.watertracker.core.toHHMM
import java.time.LocalDateTime

object NotificationScheduler {
    private const val REQUEST_CODE = 12345
    private const val EXTRA_FROM_SCHEDULER = "extra_from_scheduler"

    fun hasPermissionToSchedule(alarmManager: AlarmManager): Boolean {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()
    }

    fun hasPermissionToSchedule(context: Context): Boolean {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()
    }

    fun scheduleNext(context: Context, sendAt: LocalDateTime) {
        val epochMillis = sendAt.asTimestampWithDefaultZone()

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra(EXTRA_FROM_SCHEDULER, true)
        }

        val pending = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (!hasPermissionToSchedule(alarmManager)) {
            Log.i("WaterTrackerNotifications", "Unable to schedule notification to ${sendAt.toLocalTime().toHHMM()}: has no permission to schedule")
            return  // TODO: Handle lack of permission
        }

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, epochMillis, pending)

        Log.i("WaterTrackerNotifications", "Scheduled notification to ${sendAt.toLocalTime().toHHMM()}")
    }

    fun cancel(context: Context) {
        Log.i("WaterTrackerNotifications", "Canceling notification work")

        val intent = Intent(context, NotificationReceiver::class.java)
        val pending = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )

        if (pending != null) {
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.cancel(pending)
            pending.cancel()
        }
    }
}
