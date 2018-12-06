package com.naver.android.svc.core.common

import android.content.Context
import android.util.Log

interface ContextHolder {
    val TAG get() = javaClass.simpleName
    val context: Context?

    fun getAvaiableContext(): Context? {
        var context :Context? = null
        try{
            context = this.context
        }catch (e: UninitializedPropertyAccessException){
            Log.d(TAG, "Context not exist. Check existence of it at the moment")
        }

        if (context == null) {
            context = getMainApplicationContext()
        }
        return context
    }

    /**
     * when use getDimen or getString in contructor
     * there is no context before inflating and setting rootView
     *
     * to use getDimen or getString in constructor
     * you can override this function on your "ContextHolder implemented Class"
     */
    open fun getMainApplicationContext(): Context? {
        return null
    }
}