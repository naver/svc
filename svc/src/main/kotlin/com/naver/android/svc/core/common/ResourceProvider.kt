package com.naver.android.svc.core.common

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

interface ResourceProvider : ContextHolder {
    override val context: Context?

    fun getString(@StringRes resId: Int): String {
        val context = getAvaiableContext()
        return context?.getString(resId) ?: ""
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        val context = getAvaiableContext()
        return context?.getString(resId, *formatArgs) ?: ""
    }


    fun getColor(@ColorRes colorRes: Int): Int {
        val context = getAvaiableContext()
        context ?: return 0
        return ContextCompat.getColor(context, colorRes)
    }

    fun getDimen(@DimenRes dimenId: Int): Int {
        val context = getAvaiableContext()
        context ?: return 0
        return context.resources.getDimensionPixelSize(dimenId)
    }

    fun dpToPx(dp: Float): Int {
        val context = getAvaiableContext() ?: return 0
        val displayMetrics = context.resources.displayMetrics

        var px = (displayMetrics.density * dp).toInt()
        if (0 < dp && px == 0) {
            px = 1
        }
        return px
    }
}