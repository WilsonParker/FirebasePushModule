package com.dev.hare.firebasepushmodule.model.interfaces

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Bitmap
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat

interface Notifiable {
    fun createDefaultOwnNotification(): Notification

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createDefaultNotificationChannel(notificationManager: NotificationManager): NotificationChannel

    fun createDefaultNotificationCompatBuilder(): NotificationCompat.Builder

    @RequiresApi(Build.VERSION_CODES.O)
    fun createDefaultNotificationBuilder(): Notification.Builder

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationBuilder(): Notification.Builder

    fun createNotificationCompatBuilder(): NotificationCompat.Builder

    fun runNotification()
}