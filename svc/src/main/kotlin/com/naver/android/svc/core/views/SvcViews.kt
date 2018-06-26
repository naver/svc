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

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.support.annotation.LayoutRes
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.naver.android.svc.SvcConfig
import com.naver.android.svc.core.common.ResourceProvider
import com.naver.android.svc.core.common.Toastable

/**
 * @author bs.nam@navercorp.com 2017. 6. 8..
 */

abstract class SvcViews : LifecycleObserver, Toastable, ResourceProvider {

    val CLASS_SIMPLE_NAME = javaClass.simpleName
    var TAG: String = CLASS_SIMPLE_NAME

    lateinit var rootView: ViewGroup

    override val context: Context?
        get() = if (isInitialized) rootView.context else null

    @get:LayoutRes
    abstract val layoutResId: Int

    val isInitialized: Boolean
        get() = ::rootView.isInitialized

    val isDestroyed: Boolean
        get() = !isInitialized

    inline fun withRootView(action: View.() -> Unit) {
        if (!isInitialized) {
            return
        }

        with(rootView) {
            action()
        }
    }



    //------LifeCycle START------
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun logOnCreate() {
        if (SvcConfig.debugMode) {
            Log.d(TAG, "onCreate")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    abstract fun onCreated()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStarted() {
        if (SvcConfig.debugMode) {
            Log.d(TAG, "onStarted")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResumed() {
        if (SvcConfig.debugMode) {
            Log.d(TAG, "onResumed")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        if (SvcConfig.debugMode) {
            Log.d(TAG, "onPause")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        if (SvcConfig.debugMode) {
            Log.d(TAG, "onStop")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        if (SvcConfig.debugMode) {
            Log.d(TAG, "onDestroy")
        }
    }

    //------LifeCycle END------

    fun post(runnable: () -> Unit) {
        if (!isInitialized) {
            return
        }
        rootView.post(runnable)
    }

    fun postDelayed(runnable: Runnable, delayMillis: Int) {
        if (!isInitialized) {
            return
        }
        rootView.postDelayed(runnable, delayMillis.toLong())
    }

    fun removeCallbacks(runnable: Runnable) {
        if (!isInitialized) {
            return
        }
        rootView.removeCallbacks(runnable)
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    fun <T : View> findViewById(id: Int): T? {
        return rootView.findViewById(id)
    }
}
