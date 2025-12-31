package com.universall.watertracker.main.features.notifications

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.universall.watertracker.R
import com.universall.watertracker.main.common.Constants


object NotificationWorkerDataKeys {
    const val TITLE = "title"
    const val MESSAGE = "message"
    const val CHANNEL_ID = "channel_id"
    const val NOTIFICATIONS_ENABLED = "notifications_enabled"
}


class NotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        val title = inputData.getString(NotificationWorkerDataKeys.TITLE)!!
        val message = inputData.getString(NotificationWorkerDataKeys.MESSAGE)!!
        val channelId = inputData.getString(NotificationWorkerDataKeys.CHANNEL_ID)!!
        val notificationsEnabled = inputData.getBoolean(NotificationWorkerDataKeys.NOTIFICATIONS_ENABLED, false)

        if (!notificationsEnabled) return Result.success()

        showNotification(title = title, message = message, channelId = channelId)
        return Result.success()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showNotification(
        title: String,
        message: String,
        channelId: String
    ) {
        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.w("WaterTrackerNotificationWorker", "Unable to send notification: Permission denied")
            return
        }

        NotificationManagerCompat.from(applicationContext).notify(Constants.NOTIFICATION_ID, builder.build())
    }
}
