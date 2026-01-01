package com.universall.watertracker.main.features.notifications

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters


object NotificationWorkerDataKeys {
    const val TITLE = "title"
    const val MESSAGE = "message"
}


class NotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        val title = inputData.getString(NotificationWorkerDataKeys.TITLE)!!
        val message = inputData.getString(NotificationWorkerDataKeys.MESSAGE)!!

        showNotification(title = title, message = message)
        return Result.success()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showNotification(title: String, message: String) {
        NotificationsHelperImplST.get().sendNotification(title, message)
    }
}
