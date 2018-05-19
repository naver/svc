package com.naver.android.svc.recyclerview

import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author bs.nam@navercorp.com 2017. 6. 19..
 */

abstract class BaseBindHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindView(item: T, position: Int)

    fun getString(@StringRes resId: Int): String {
        return itemView.context.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return itemView.context.getString(resId, *formatArgs)
    }

    fun getColor(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(itemView.context, resId)
    }
}
