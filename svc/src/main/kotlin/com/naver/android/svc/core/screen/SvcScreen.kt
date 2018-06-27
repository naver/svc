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

package com.naver.android.svc.core.screen

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.naver.android.svc.core.controltower.SvcCT
import com.naver.android.svc.core.views.SvcViews
import com.naver.android.svc.core.views.UseCase
import com.naver.android.svc.core.views.UseCaseViews

/**
 * @author bs.nam@navercorp.com 2018. 2. 21..
 */
interface SvcScreen<out V : SvcViews, out C> : LifecycleOwner {

    /**
     * every screen can access to their host Activity
     * because fragment's getActivity() method is final method
     * I had to change name "activity" as "hostActivity"
     */
    val hostActivity: FragmentActivity?

    val screenFragmentManager: FragmentManager?

    val isActive: Boolean

    fun createViews(): V
    fun createControlTower(): C

    /**
     * add dependency of screen and usecase
     */
    fun <V : SvcViews, C : SvcCT<*, *>> initializeSVC(svcScreen: SvcScreen<*, *>, views: V, ct: C) {
        views.apply {
            views.screen = svcScreen

            if (this is UseCaseViews<*> && ct is UseCase) {
                setControllerUsecase(ct)
            }
        }
    }

}