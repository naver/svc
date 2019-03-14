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

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.naver.android.svc.core.controltower.ControlTower
import com.naver.android.svc.core.views.Views


/**
 * @author bs.nam@navercorp.com 2017. 6. 8..
 */

abstract class SvcActivity<out V : Views, out C : ControlTower<*, *>> : AppCompatActivity(),
        Screen<V, C>,
        DialogSupportScreen,
        StatusbarChanger{

    var CLASS_SIMPLE_NAME = javaClass.simpleName
    val TAG: String = CLASS_SIMPLE_NAME

    val views by lazy { createViews() }
    val controlTower by lazy { createControlTower() }

    override val hostActivity: FragmentActivity?
        get() = this

    override val fragmentManagerForDialog: FragmentManager?
        get() = this.supportFragmentManager

    override val screenFragmentManager: FragmentManager?
        get() = this.supportFragmentManager

    override var statusbarColor: Int? = null
        set(value) {
            field = value
            setStatusBarBGColor(value)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.layoutResId)
        initializeSVC(this, views, controlTower)

        val rootView: FrameLayout = window.decorView.findViewById(android.R.id.content)
        views.rootView = rootView
        statusbarColor = statusbarColor

        lifecycle.addObserver(views)
        lifecycle.addObserver(controlTower)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(controlTower)
        lifecycle.removeObserver(views)
    }

    override fun onBackPressed() {
        if (controlTower.onBackPressed() || views.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }

    override val isActive: Boolean
        get() = !isFinishing

}
