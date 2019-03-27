package com.dev.hare.firebasepushmodule.model.abstracts

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.dev.hare.firebasepushmodule.MainActivity
import com.dev.hare.firebasepushmodule.R
import com.dev.hare.firebasepushmodule.model.interfaces.Notifiable

abstract class AbstractNotificationModel(private val context: Context, private val data: Map<String, String>) : Notifiable {
    companion object {
        const val KEY_TITLE = "title"
        const val KEY_CONTENT = "content"
        const val KEY_URL = "url"
    }

    private val _CHANNEL_ID = "channelID"
    private val _CHANNEL_NAME = "channelName"

    private var title: String? = null
    private var content: String? = null
    private var url: String? = null

    private var notificationManager: NotificationManager = context.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
    private var notificationBuilder: Notification.Builder? = null
    private var notificationCompatBuilder: NotificationCompat.Builder? = null

    init {
        this.title = data[KEY_TITLE]
        this.content = data[KEY_CONTENT]
        this.url = data[KEY_URL]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.notificationBuilder = createDefaultNotificationBuilder()
            createNotificationChannel(notificationManager)
        } else {
            this.notificationCompatBuilder = createDefaultNotificationCompatBuilder()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createOwnNotification(): Notification {
        notificationBuilder = Notification.Builder(context, _CHANNEL_ID)
        return notificationBuilder?.apply {
            setOngoing(true)
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(title)
            setContentText(content)
            setCategory(Notification.CATEGORY_SERVICE)
            setContentIntent(createPendingIntent())
        }!!.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotificationChannel(notificationManager: NotificationManager): NotificationChannel {
        var notificationChannel: NotificationChannel =
            NotificationChannel(_CHANNEL_ID, _CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
                enableLights(true)
                enableVibration(true)
                importance = NotificationManager.IMPORTANCE_DEFAULT
            }
        notificationManager.createNotificationChannel(notificationChannel)
        return notificationChannel
    }

    override fun createDefaultNotificationCompatBuilder(): NotificationCompat.Builder {
        notificationCompatBuilder = NotificationCompat.Builder(context, _CHANNEL_ID)
        return notificationCompatBuilder!!.apply {
            setOngoing(true)
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(title)
            setContentText(content)
            setCategory(Notification.CATEGORY_SERVICE)
            setContentIntent(createPendingIntent())
        }
    }

    override fun createDefaultNotificationCompatBuilderWithImage(image: Bitmap): NotificationCompat.Builder {
        if(notificationCompatBuilder == null)
            createDefaultNotificationCompatBuilder()
        notificationCompatBuilder?.apply {
            setStyle(NotificationCompat.BigPictureStyle().bigPicture(image)
                .setBigContentTitle(title)
                .setSummaryText(content))
        }
        return notificationCompatBuilder!!
    }

    abstract fun createNotificationCompatBuilder(image: Bitmap? = null): NotificationCompat.Builder

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createDefaultNotificationBuilder(): Notification.Builder {
        notificationBuilder = Notification.Builder(context, _CHANNEL_ID)
        return notificationBuilder!!.apply {
            setOngoing(true)
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(title)
            setContentText(content)
            setCategory(Notification.CATEGORY_SERVICE)
            setContentIntent(createPendingIntent())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createDefaultNotificationBuilderWithImage(image: Bitmap): Notification.Builder {
        if(notificationBuilder == null)
            createDefaultNotificationBuilder()
        notificationBuilder?.apply {
            style = Notification.BigPictureStyle().bigPicture(image)
                .setBigContentTitle(title)
                .setSummaryText(content)
        }
        return this.notificationBuilder!!
    }

    abstract fun createNotificationBuilder(image: Bitmap? = null): NotificationCompat.Builder

    override fun runNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.notify(0, notificationBuilder!!.build())
        }else {
            notificationManager.notify(0, notificationCompatBuilder!!.build())
        }
    }

    private fun createPendingIntent(): PendingIntent {
        val t = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(
            context,
            0,
            t.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

}