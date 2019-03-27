package com.dev.hare.firebasepushmodule.util

import android.graphics.Bitmap
import android.os.AsyncTask
import com.dev.hare.firebasepushmodule.exception.ExceptionUtil

class ImageUtilUsingThread : ImageUtil() {
    private var onImageLoadCompleteListener: OnImageLoadCompleteListener? = null
    private var def: Bitmap? = null
    private var onErrorUtil: ExceptionUtil? = null

    fun urlToBitmapUsingThread(strUrl: String, def: Bitmap?, onImageLoadCompleteListener: OnImageLoadCompleteListener) {
        this.def = def
        this.onImageLoadCompleteListener = onImageLoadCompleteListener
        ImageLoadTask().execute(strUrl)
    }

    interface OnImageLoadCompleteListener {
        fun onComplete(bitmap: Bitmap)
    }

    private inner class ImageLoadTask : AsyncTask<String, Void, Bitmap>() {
        override fun doInBackground(vararg strings: String): Bitmap? {
            try {
                onErrorUtil = ExceptionUtil(
                    6,
                    onExecute = object : ExceptionUtil.OnExecute {
                        override fun <T> execute(): T {
                            var bitmap = urlToBitmap(strings[0])
                            return bitmap as T
                        }
                    }
                )
                return onErrorUtil!!.handleException(def)
            } catch (e: Exception) {
                return null
            }

        }

        override fun onPostExecute(bitmap: Bitmap) {
            onImageLoadCompleteListener!!.onComplete(bitmap)
        }
    }

}