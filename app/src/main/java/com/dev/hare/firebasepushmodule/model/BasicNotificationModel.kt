package com.dev.hare.firebasepushmodule.model

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.app.NotificationCompat
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractNotificationModel

class BasicNotificationModel(context: Context, data: Map<String, String>) : AbstractNotificationModel(context, data) {

    override fun createNotificationCompatBuilder(image: Bitmap?): NotificationCompat.Builder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createNotificationBuilder(image: Bitmap?): NotificationCompat.Builder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}