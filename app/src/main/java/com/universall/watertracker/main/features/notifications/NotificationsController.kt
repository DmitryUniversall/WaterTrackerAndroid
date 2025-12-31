package com.universall.watertracker.main.features.notifications

import android.content.Context
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NotificationController(
    private val context: Context,
    private val settingsService: SettingsService
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun launch() {
        scope.launch {
            settingsService.settingsFlow.collect { settings ->
                if (settings.notificationsEnabled) {
                    NotificationsHelper.scheduleNotificationsWork(context, settings.reminderInterval.toLong())
                } else {
                    NotificationsHelper.cancelNotificationsWork(context)
                }
            }
        }
    }
}
