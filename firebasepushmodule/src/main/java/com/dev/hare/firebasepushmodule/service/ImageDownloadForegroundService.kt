package com.dev.hare.firebasepushmodule.service

import android.graphics.Bitmap
import android.os.Build
import android.support.annotation.RequiresApi
import com.dev.hare.firebasepushmodule.model.BasicNotificationModel
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractDefaultNotificationModel
import com.dev.hare.firebasepushmodule.service.abstracts.AbstractImageDownloadService
import com.dev.hare.firebasepushmodule.util.ImageUtilUsingThread

class ImageDownloadForegroundService : AbstractImageDownloadService() {

    override fun createOnImageLoadCompleteListener(): ImageUtilUsingThread.OnImageLoadCompleteListener {
        return object : ImageUtilUsingThread.OnImageLoadCompleteListener {

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onComplete(bitmap: Bitmap?) {
                model!!.img = bitmap;
                model!!.createNotificationBuilder()
                model!!.runNotification()
                notifyStop()
            }
        }
    }

    override fun createNotificationModel(data: Map<String, String>?): AbstractDefaultNotificationModel {
        return BasicNotificationModel(
            this,
            data ?: mutableMapOf()
        )
    }

}