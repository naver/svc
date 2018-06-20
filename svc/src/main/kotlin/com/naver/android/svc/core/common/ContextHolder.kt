package com.naver.android.svc.core.common

import android.content.Context

interface ContextHolder {
    val context: Context?

    fun getAvaiableContext(): Context? {
        var context = context
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