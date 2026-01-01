package com.universall.watertracker.main.features.notifications

import android.app.Notification

interface NotificationsHelper {
    val channelId : String
    val channelName : String
    val notificationId : Int

    fun createChannel()

    fun buildNotification(title: String, message: String): Notification
    fun sendNotification(notification: Notification)
    fun sendNotification(title: String, message: String)

    fun isPermissionGranted(): Boolean
    fun isSystemNotificationsEnabled(): Boolean
    fun isAllowedToSendNotifications(): Boolean
}
