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

abstract class AbstractNotificationModel(
    private val context: Context,
    data: Map<String, String>,
    private val channelID: String = "channelID",
    private val channelName: String = "channelName"
) : Notifiable {

    companion object {
        const val KEY_TITLE = "title"
        const val KEY_CONTENT = "content"
        const val KEY_URL = "url"
    }

    protected val _REQUEST_CODE = 0

    protected var title: String? = null
    protected var content: String? = null
    protected var url: String? = null
    protected var notificationManager: NotificationManager =
        context.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
    protected var notificationBuilder: Notification.Builder? = null
    protected var notificationCompatBuilder: NotificationCompat.Builder? = null

    var img: Bitmap? = null

    init {
        this.title = data[KEY_TITLE]
        this.content = data[KEY_CONTENT]
        this.url = data[KEY_URL]

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.notificationBuilder = createNotificationBuilder();
            if (this.notificationBuilder == null) {
                this.notificationBuilder = createDefaultNotificationBuilder()
            }
            createNotificationChannel(notificationManager)
        } else {
            this.notificationCompatBuilder = createNotificationCompatBuilder()
            if (this.notificationCompatBuilder == null) {
                this.notificationCompatBuilder = createDefaultNotificationCompatBuilder()
            }
        }
    }

    /**
     * create default foreground Notification
     *
     * @param
     * @return
     * @author Hare
     * @added 28/03/2019
     * @updated 28/03/2019
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun createOwnNotification(): Notification {
        notificationBuilder = Notification.Builder(context, channelID)
        return notificationBuilder?.apply {
            setOngoing(true)
            setContentTitle(title)
            setContentText("App is running")
            setCategory(Notification.CATEGORY_SERVICE)
            setContentIntent(createDefaultPendingIntent())
        }!!.build()
    }

    /**
     * create NotificationChannel in NotificationManager
     *
     * @param
     * @return
     * @author Hare
     * @added 28/03/2019
     * @updated 28/03/2019
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotificationChannel(notificationManager: NotificationManager): NotificationChannel {
        var notificationChannel: NotificationChannel =
            NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
                enableLights(true)
                enableVibration(true)
                importance = NotificationManager.IMPORTANCE_DEFAULT
            }
        notificationManager.createNotificationChannel(notificationChannel)
        return notificationChannel
    }

    /**
     * create NotificationCompat.Builder by default
     *
     * @param
     * @return
     * @author Hare
     * @added 28/03/2019
     * @updated 28/03/2019
     * */
    override fun createDefaultNotificationCompatBuilder(): NotificationCompat.Builder {
        notificationCompatBuilder = NotificationCompat.Builder(context, channelID)
        return notificationCompatBuilder!!.apply {
            setOngoing(true)
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(title)
            setContentText(content)
            setCategory(Notification.CATEGORY_SERVICE)
            setContentIntent(createDefaultPendingIntent())
        }
    }

    /**
     * create Notification.Builder by default
     *
     * @param
     * @return
     * @author Hare
     * @added 28/03/2019
     * @updated 28/03/2019
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun createDefaultNotificationBuilder(): Notification.Builder {
        notificationBuilder = Notification.Builder(context, channelID)
        return notificationBuilder!!.apply {
            setOngoing(true)
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(title)
            setContentText(content)
            setCategory(Notification.CATEGORY_SERVICE)
            setContentIntent(createDefaultPendingIntent())
        }
    }

    fun applyDefaultBigPictureStyle(builder: NotificationCompat.Builder, image: Bitmap): NotificationCompat.Builder {
        return builder?.apply {
            setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(image)
                    .setBigContentTitle(title)
                    .setSummaryText(content)
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun applyDefaultBigPictureStyle(builder: Notification.Builder, image: Bitmap): Notification.Builder {
        return builder?.apply {
            style = Notification.BigPictureStyle()
                .bigPicture(image)
                .setBigContentTitle(title)
                .setSummaryText(content)
        }
    }

    fun applyDefaultBigTextStyle(builder: NotificationCompat.Builder): NotificationCompat.Builder {
        return builder?.apply {
            setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(content)
                    .setBigContentTitle(title)
                    .setSummaryText(content)
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun applyDefaultBigTextStyle(builder: Notification.Builder): Notification.Builder {
        return builder?.apply {
            style = Notification.BigTextStyle()
                .bigText(content)
                .setBigContentTitle(title)
                .setSummaryText(content)
        }
    }

    override fun runNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.notify(0, notificationBuilder!!.build())
        } else {
            notificationManager.notify(0, notificationCompatBuilder!!.build())
        }
    }

    private fun createDefaultPendingIntent(): PendingIntent {
        val t = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(
            context,
            _REQUEST_CODE,
            t.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

}