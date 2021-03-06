package com.dev.hare.firebasepushmodule.model.interfaces

import android.app.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import kotlin.reflect.KClass

interface NotificationBuildable {
    fun createDefaultOwnNotification(): Notification

    fun createOwnNotification(): Notification

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createDefaultNotificationChannel(notificationManager: NotificationManager): NotificationChannel

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createNotificationChannel(notificationManager: NotificationManager): NotificationChannel

    fun createDefaultNotificationCompatBuilder(): NotificationCompat.Builder

    fun createNotificationCompatBuilder(): NotificationCompat.Builder

    @RequiresApi(Build.VERSION_CODES.O)
    fun createDefaultNotificationBuilder(): Notification.Builder

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationBuilder(): Notification.Builder

    fun createDefaultPendingIntent(activity: Class<out Activity>): PendingIntent

    fun createPendingIntent(activity: Class<out Activity>): PendingIntent

}