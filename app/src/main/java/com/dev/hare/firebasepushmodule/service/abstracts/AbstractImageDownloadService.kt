package com.dev.hare.firebasepushmodule.service.abstracts

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractNotificationModel
import com.dev.hare.firebasepushmodule.model.interfaces.Notifiable
import com.google.firebase.messaging.RemoteMessage
import java.lang.NullPointerException

abstract class AbstractImageDownloadService : Service() {
    companion object {
        const val KEY_URL = "url"
        const val KEY_REMOTE_MESSAGE = "msg"
        private const val CHANNEL_ID= 1
    }

    protected var url: String? = null
    protected var data: Map<String, String>? = null
    protected var model: AbstractNotificationModel? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        runAfterBind(intent)
        return Service.START_REDELIVER_INTENT
    }

    /**
     * RemoteMessage 를 이용해서 url, data property 에 값을 대입 한 후 run() 을 실행합니다
     *
     * @Author : Hare
     * Update : 19.3.27
     */
    private fun runAfterBind(intent: Intent?) {
        intent?.let {
            url = it.getStringExtra(KEY_URL)
            data = (it.getParcelableExtra(KEY_REMOTE_MESSAGE) as RemoteMessage).data
            model = createNotificationModel(data)
            run()
        }
    }

    /**
     * Oreo 버전 이후 부터는 빈 Notification 을 사용할 수 없기 때문에
     * 새로운 Notification 을 생성하여 실행해야 합니다
     *
     * @Author : Hare
     * Update : 19.3.27
     */
    @Throws(NullPointerException::class)
    internal fun notifyOwn(model: Notifiable?) {
        if(model == null)
            throw NullPointerException("Notifiable is null")
        startForeground(CHANNEL_ID,
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
                model.createOwnNotification()
            else
                Notification()
        )
    }

    internal fun notifyStop() {
        stopForeground(true)
        stopSelf(CHANNEL_ID)
    }

    internal abstract fun createNotificationModel(data: Map<String, String>?): AbstractNotificationModel
    internal abstract fun run()

}
