package com.dev.hare.firebasepushmodule.service

import android.graphics.Bitmap
import android.os.Build
import android.support.annotation.RequiresApi
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractNotificationModel
import com.dev.hare.firebasepushmodule.model.interfaces.Notifiable
import com.dev.hare.firebasepushmodule.service.abstracts.AbstractImageDownloadService
import com.dev.hare.firebasepushmodule.util.ImageUtilUsingThread
import com.dev.hare.firebasepushmodule.util.Logger

class ImageDownloadForegroundService : AbstractImageDownloadService() {

    /**
     * Foreground 로 Notification 을 실행하고 푸시를 보냅니다
     *
     * @Author : Hare
     * Update : 19.3.27
     */
    override fun run() {
        try {
            notifyOwn(model)
            model!!.let { m ->
                val imageUtilUsingThread = ImageUtilUsingThread()
                imageUtilUsingThread.urlToBitmapUsingThread(
                    url!!,
                    null,
                    object : ImageUtilUsingThread.OnImageLoadCompleteListener {
                        @RequiresApi(Build.VERSION_CODES.O)
                        override fun onComplete(bitmap: Bitmap) {
                            m.createNotificationBuilder(bitmap)
                            m.runNotification()
                            notifyStop()
                        }
                    })
            }
        } catch (e: Exception) {
            Logger.log(Logger.LogType.ERROR, e)
        }
    }

    override fun createNotificationModel(data: Map<String, String>?): AbstractNotificationModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}