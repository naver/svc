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
package com.naver.android.svc.core.common

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * @author bs.nam@navercorp.com
 */
interface ResourceProvider : ContextHolder {
    override val context: Context?

    fun getString(@StringRes resId: Int): String {
        val context = getAvailableContext()
        return context?.getString(resId) ?: ""
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        val context = getAvailableContext()
        return context?.getString(resId, *formatArgs) ?: ""
    }

    fun getColor(@ColorRes colorRes: Int): Int {
        val context = getAvailableContext()
        context ?: return 0
        return ContextCompat.getColor(context, colorRes)
    }

    fun getDimen(@DimenRes dimenId: Int): Int {
        val context = getAvailableContext()
        context ?: return 0
        return context.resources.getDimensionPixelSize(dimenId)
    }

    fun getDrawable(@DrawableRes drawableId: Int): Drawable? {
        val context = getAvailableContext()
        context ?: return null
        return ContextCompat.getDrawable(context, drawableId)
    }

    fun dpToPx(dp: Float): Int {
        val context = getAvailableContext() ?: return 0
        val displayMetrics = context.resources.displayMetrics

        var px = (displayMetrics.density * dp).toInt()
        if (0 < dp && px == 0) {
            px = 1
        }
        return px
    }
}
