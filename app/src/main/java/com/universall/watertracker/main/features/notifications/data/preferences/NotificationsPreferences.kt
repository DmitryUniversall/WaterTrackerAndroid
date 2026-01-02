package com.universall.watertracker.main.features.notifications.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.notificationsDataStore by preferencesDataStore(name = "notifications")


object NotificationsPreferences {
    val SCHEDULED_NOTIFICATION_TIMESTAMP = longPreferencesKey("scheduled_notification_timestamp")
    val SCHEDULED_NOTIFICATION_TITLE = stringPreferencesKey("scheduled_notification_title")
    val SCHEDULED_NOTIFICATION_MESSAGE = stringPreferencesKey("scheduled_notification_message")
}
