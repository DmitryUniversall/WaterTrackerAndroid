package com.universall.watertracker.core.background_tasks

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.universall.watertracker.core.toLocalDateTime
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

class WorkScheduler(
    private val context: Context,
    private val workName: String
) {
    fun isWorkScheduled(): Boolean {
        val infos = WorkManager.getInstance(context)
            .getWorkInfosForUniqueWork(workName)
            .get()

        return infos.any { info ->
            when (info.state) {
                WorkInfo.State.ENQUEUED, WorkInfo.State.RUNNING -> true
                else -> false
            }
        }
    }

    fun runWorkerAt(worker: KClass<out CoroutineWorker>, whenUtcMillis: Long, data: Data? = null) {
        Log.i("WorkScheduler", "Scheduling work '$workName' to: ${whenUtcMillis.toLocalDateTime()}")

        val delay = maxOf(0, whenUtcMillis - System.currentTimeMillis())
        val work = OneTimeWorkRequest.Builder(worker.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data ?: Data.EMPTY)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                workName,
                ExistingWorkPolicy.REPLACE,
                work
            )
    }

    fun cancelWorker() {
        Log.i("WorkScheduler", "Cancelling work '$workName'")

        val workManager = WorkManager.getInstance(context)
        workManager.cancelUniqueWork(uniqueWorkName = workName)
    }
}
