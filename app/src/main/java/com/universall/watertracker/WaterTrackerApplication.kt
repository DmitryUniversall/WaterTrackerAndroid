package com.universall.watertracker

import android.app.Application
import com.universall.watertracker.main.common.db.AppDatabase
import com.universall.watertracker.main.features.notifications.NotificationsHelper
import com.universall.watertracker.main.features.notifications.NotificationsHelperImplST
import com.universall.watertracker.main.features.notifications.NotificationsScheduler
import com.universall.watertracker.main.features.notifications.NotificationsSchedulerImplST
import com.universall.watertracker.main.features.settings.data.repositories.SettingsRepositoryImplST
import com.universall.watertracker.main.features.settings.domain.services_impl.SettingsServiceImplST
import com.universall.watertracker.main.features.stats.data.repositories.StatsRepositoryImplST
import com.universall.watertracker.main.features.stats.domain.services_impl.StatsServiceImplST
import com.universall.watertracker.main.features.water_tracker.domain.services_impl.WaterTrackerServiceImplST

class WaterTrackerApplication : Application() {
    private lateinit var notificationsHelper: NotificationsHelper
    private lateinit var notificationsScheduler: NotificationsScheduler

    override fun onCreate() {
        super.onCreate()
        initComponents()
        launchNotificationsWork()
    }

    private fun initComponents() {
        initRepositories()
        initServices()
    }

    private fun initRepositories() {
        SettingsRepositoryImplST.init(this)
        StatsRepositoryImplST.init(AppDatabase.getInstance(this).waterIntakeDao())
    }

    private fun initServices() {
        SettingsServiceImplST.init(SettingsRepositoryImplST.get())
        StatsServiceImplST.init(StatsRepositoryImplST.get())
        WaterTrackerServiceImplST.init(StatsServiceImplST.get(), SettingsServiceImplST.get())
    }

    private fun launchNotificationsWork() {
        NotificationsHelperImplST.init(this)
        notificationsHelper = NotificationsHelperImplST.get()
        notificationsHelper.createChannel()

        NotificationsSchedulerImplST.init(this, SettingsServiceImplST.get())
        notificationsScheduler = NotificationsSchedulerImplST.get()
        notificationsScheduler.launch()
    }
}
