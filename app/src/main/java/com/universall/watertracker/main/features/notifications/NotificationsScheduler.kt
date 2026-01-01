package com.universall.watertracker.main.features.notifications

interface NotificationsScheduler {
    fun scheduleNotificationsWork(interval: Long)
    fun cancelNotificationsWork()
    fun launch()
}
