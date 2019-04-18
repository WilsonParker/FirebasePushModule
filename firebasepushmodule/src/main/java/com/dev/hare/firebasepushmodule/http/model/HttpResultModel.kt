package com.dev.hare.firebasepushmodule.http.model

import com.google.gson.Gson

object HttpResultModel {
    private val _gson = Gson()
    var code: String? = null
    var message: String? = null

    override fun toString(): String {
        return _gson.toJson(this)
    }
}