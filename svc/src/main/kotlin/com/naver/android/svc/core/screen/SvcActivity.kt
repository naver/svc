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

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.naver.android.svc.core.controltower.SvcCT
import com.naver.android.svc.core.views.SvcViews
import com.naver.android.svc.core.views.UseCase
import com.naver.android.svc.core.views.UseCaseViews


/**
 * @author bs.nam@navercorp.com 2017. 6. 8..
 */

abstract class SvcActivity<out V : SvcViews, out C : SvcCT<*, *>> : AppCompatActivity(), SvcScreen<V, C>, DialogPlug {

    var CLASS_SIMPLE_NAME = javaClass.simpleName
    val TAG: String = CLASS_SIMPLE_NAME

    override val views by lazy { createViews() }
    override val ct by lazy { createControlTower() }
    override val hostActivity: FragmentActivity?
        get() = this
    override val fragmentManagerForDialog: FragmentManager?
        get() = this.supportFragmentManager


    open var statusbarColor: Int? = null

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarBGColor(bgColor: Int?) {
        if (bgColor == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }

        window.statusBarColor = bgColor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(views.layoutResId)

        val finalViews = views
        val finalController = ct

        val rootView: FrameLayout = window.decorView.findViewById(android.R.id.content)
        views.rootView = rootView

        if (finalViews is UseCaseViews<*> && finalController is UseCase) {
            finalViews.setControllerUsecase(finalController)
        }

        setStatusBarBGColor(statusbarColor)

        super.onCreate(savedInstanceState)
        lifecycle.addObserver(views)
        lifecycle.addObserver(ct)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(ct)
        lifecycle.removeObserver(views)
    }

    override fun onBackPressed() {
        if (ct.onBackPressed() || views.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }

    override val isActive: Boolean
        get() = !isFinishing

}
