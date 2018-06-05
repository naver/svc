/*
 * Copyright 2018 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.naver.android.svc.recyclerview

import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author bs.nam@navercorp.com 2017. 6. 19..
 */

abstract class SvcHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
