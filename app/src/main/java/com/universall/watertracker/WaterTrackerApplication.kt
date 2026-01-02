package com.universall.watertracker

import android.app.Application
import com.universall.watertracker.main.common.db.AppDatabase
import com.universall.watertracker.main.features.notifications.data.repositories.NotificationsRepositoryImplST
import com.universall.watertracker.main.features.notifications.domain.services_impl.NotificationsServiceImplST
import com.universall.watertracker.main.features.settings.data.repositories.SettingsRepositoryImplST
import com.universall.watertracker.main.features.settings.domain.services_impl.SettingsServiceImplST
import com.universall.watertracker.main.features.stats.data.repositories.StatsRepositoryImplST
import com.universall.watertracker.main.features.stats.domain.services_impl.StatsServiceImplST
import com.universall.watertracker.main.features.water_tracker.domain.services_impl.WaterTrackerServiceImplST

class WaterTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initComponents()
        launchNotificationsWork()
    }

    private fun initComponents() {
        SettingsRepositoryImplST.init(this)
        SettingsServiceImplST.init(SettingsRepositoryImplST.get())

        StatsRepositoryImplST.init(AppDatabase.getInstance(this).waterIntakeDao())
        StatsServiceImplST.init(StatsRepositoryImplST.get())

        NotificationsRepositoryImplST.init(this, SettingsServiceImplST.get())
        NotificationsServiceImplST.init(NotificationsRepositoryImplST.get())

        WaterTrackerServiceImplST.init(StatsServiceImplST.get(), SettingsServiceImplST.get())
    }

    private fun launchNotificationsWork() {
        NotificationsServiceImplST.get().launchNotificationsWork()
    }
}
