package com.universall.watertracker.main.features.notifications.data.worker

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.universall.watertracker.main.features.notifications.data.repositories.NotificationsRepositoryImplST
import com.universall.watertracker.main.features.notifications.domain.repositories.NotificationsRepository


class NotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {
    private lateinit var notificationsRepository: NotificationsRepository

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        notificationsRepository = NotificationsRepositoryImplST.get()
        if (!notificationsRepository.isNotificationsEnabledAndAllowed()) return Result.success()

        showNotification()

        return Result.success()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private suspend fun showNotification() {
        notificationsRepository.sendScheduledNotification()
    }
}
