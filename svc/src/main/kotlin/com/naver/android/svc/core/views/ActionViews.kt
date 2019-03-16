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
package com.naver.android.svc.core.views

abstract class ActionViews<VA : ViewsAction> : Views() {
    lateinit var viewsAction: VA

    fun setAction(viewsAction: ViewsAction) {
        try {
            @Suppress("UNCHECKED_CAST")
            this.viewsAction = viewsAction as VA
        } catch (e: Exception) {
            throw IllegalStateException("viewsAction type mismatch \n ${viewsAction.javaClass.simpleName} are using wrong viewsAction")
        }
    }
}
