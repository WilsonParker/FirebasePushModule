package com.dev.hare.entersixpushtest

import android.app.*
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractDefaultNotificationModel

class FCMModel(context: Context, data: Map<String, String>, channelID: String, channelName: String) :
    AbstractDefaultNotificationModel(context, data, channelID, channelName) {

    init {
        title = data.get(AbstractDefaultNotificationModel.KEY_TITLE)
        content = data.get(AbstractDefaultNotificationModel.KEY_CONTENT)
        imageUrl = data.get(AbstractDefaultNotificationModel.KEY_IMAGE_URL)
        link = data.get(AbstractDefaultNotificationModel.KEY_LINK)
    }

    override fun applyNotificationCompatBuilder(notificationCompatBuilder: NotificationCompat.Builder): NotificationCompat.Builder {
        return notificationCompatBuilder.setStyle(NotificationCompat.BigPictureStyle(notificationCompatBuilder).run {
            setBigContentTitle(title)
            setSummaryText(content)
            bigPicture(img)
        })
    }

    override fun applyNotificationBuilder(notificationBuilder: Notification.Builder): Notification.Builder {
        return notificationBuilder.setStyle(Notification.BigPictureStyle(notificationBuilder).run {
            setBigContentTitle(title)
            setSummaryText(content)
            bigPicture(img)
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createOwnNotification(): Notification {
        return createDefaultOwnNotification()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotificationChannel(notificationManager: NotificationManager): NotificationChannel {
        return createDefaultNotificationChannel(notificationManager)
    }

    override fun createNotificationCompatBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelID).apply {
            setContentTitle(title)
            setContentText(content)
            setSmallIcon(R.mipmap.ic_launcher)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
            priority = Notification.PRIORITY_MAX
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotificationBuilder(): Notification.Builder {
        return Notification.Builder(context, channelID).apply {
            setContentTitle(title)
            setContentText(content)
            setSmallIcon(R.mipmap.ic_launcher)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
        }
    }

    override fun createPendingIntent(activity: Class<out Activity>): PendingIntent {
        return createDefaultPendingIntent(activity)
    }
}