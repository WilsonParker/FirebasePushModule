package com.dev.hare.entersixpushtest

import com.dev.hare.firebasepushmodule.util.Logger
import com.google.firebase.iid.FirebaseInstanceId

object FCMUtil {

    fun getToken(){
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            Logger.log(Logger.LogType.INFO, "token = ${it.token}")
        }
    }
}