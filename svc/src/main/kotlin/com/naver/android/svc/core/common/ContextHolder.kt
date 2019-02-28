/*
 * Copyright 2018 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.naver.android.svc.core.common

import android.content.Context

/**
 * @author bs.nam@navercorp.com
 */
interface ContextHolder {
    val context: Context?

    fun getAvailableContext(): Context? {
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
