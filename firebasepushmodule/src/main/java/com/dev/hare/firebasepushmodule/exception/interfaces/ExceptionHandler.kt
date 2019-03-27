package com.dev.hare.firebasepushmodule.exception.interfaces

interface ExceptionHandler {
    open fun <T> handleException(def: T?): T?
}
