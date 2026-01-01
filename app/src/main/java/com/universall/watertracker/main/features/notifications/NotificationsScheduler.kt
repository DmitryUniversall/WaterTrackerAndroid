package com.universall.watertracker.main.features.notifications

interface NotificationsScheduler {
    fun scheduleNotificationsWork(interval: Long, title: String, message: String)
    fun cancelNotificationsWork()
    fun launch()
}
