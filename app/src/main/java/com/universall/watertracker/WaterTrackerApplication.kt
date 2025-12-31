package com.universall.watertracker

import android.app.Application
import com.universall.watertracker.main.features.notifications.NotificationController
import com.universall.watertracker.main.features.settings.data.repositories.SettingsRepositoryImpl
import com.universall.watertracker.main.features.settings.domain.services.SettingsService
import com.universall.watertracker.main.features.settings.domain.services_impl.SettingsServiceImplST

class WaterTrackerApplication : Application() {
    private lateinit var settingsService: SettingsService
    private lateinit var notificationController: NotificationController

    override fun onCreate() {
        super.onCreate()

        settingsService = SettingsServiceImplST(
            repository = SettingsRepositoryImpl(
                context = this
            )
        )

        notificationController = NotificationController(
            context = this,
            settingsService = settingsService
        )

        launchNotificationsWork()
    }

    private fun initServices() {

    }

    private fun launchNotificationsWork() {
        notificationController.launch()
    }
}
