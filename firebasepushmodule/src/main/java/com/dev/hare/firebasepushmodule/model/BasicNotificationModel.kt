package com.dev.hare.firebasepushmodule.model

import android.app.*
import android.content.Context
import android.support.v4.app.NotificationCompat
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractDefaultNotificationModel

class BasicNotificationModel : AbstractDefaultNotificationModel {

    constructor(context: Context, data: Map<String, String>, channelID: String, channelName: String) : super(context, data, channelID, channelName)

    constructor(context: Context, data: Map<String, String>) : super(context, data)

    override fun createOwnNotification(): Notification {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createNotificationChannel(notificationManager: NotificationManager): NotificationChannel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createNotificationCompatBuilder(): NotificationCompat.Builder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createNotificationBuilder(): Notification.Builder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun applyNotificationCompatBuilder(notificationCompatBuilder: NotificationCompat.Builder): NotificationCompat.Builder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun applyNotificationBuilder(notificationBuilder: Notification.Builder): Notification.Builder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createPendingIntent(activity: Class<out Activity>): PendingIntent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}