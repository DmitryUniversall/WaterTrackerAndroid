package com.universall.watertracker.main.features.notifications

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

class AppNotificationsState(
    private val requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,

    val permissionGranted: Boolean,
    val systemAllowed: Boolean,
) {
    val isReady get() = permissionGranted && systemAllowed

    fun requestNotificationsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !permissionGranted) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}


@Composable
fun rememberNotificationsState(): AppNotificationsState {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var systemAllowed by remember { mutableStateOf(true) }
    var permissionGranted by remember { mutableStateOf(true) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted = granted
        systemAllowed = NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                permissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
                } else true

                systemAllowed = NotificationManagerCompat.from(context).areNotificationsEnabled()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    return AppNotificationsState(
        requestPermissionLauncher = requestPermissionLauncher,
        permissionGranted = permissionGranted,
        systemAllowed = systemAllowed
    )
}
