package com.dev.hare.firebasepushmodule.model

import android.content.Context
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractNotificationBigStyleModel

class NotificationDataModel(
    protected val context: Context,
    protected val channelID: String = "channelID",
    protected val channelName: String = "channelName"
) {
    protected var title: String? = null
    protected var content: String? = null
    protected var link: String? = null
    protected var imageUrl: String? = null
    protected var pushType: String? = null

    protected var bigStyle: AbstractNotificationBigStyleModel? = null


    companion object {
        const val KEY_TITLE = "title"
        const val KEY_CONTENT = "content"
        const val KEY_IMAGE_URL = "imageUrl"
        const val KEY_LINK = "link"
        const val KEY_PUSH_TYPE = "push_type"
    }
}