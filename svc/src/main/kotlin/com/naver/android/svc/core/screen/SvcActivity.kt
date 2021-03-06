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
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 * @author bs.nam@navercorp.com 2017. 6. 8..
 */

@Suppress("PrivatePropertyName")
abstract class SvcActivity : AppCompatActivity(), Screen, DialogSupportScreen {

    private var CLASS_SIMPLE_NAME = javaClass.simpleName
    private val TAG: String = CLASS_SIMPLE_NAME

    override val baseViews by lazy { createViews() }
    //use base name for using casting as "controlTower" later
    override val baseControlTower by lazy { createControlTower() }

    override val hostActivity: FragmentActivity?
        get() = this

    override val fragmentManagerForDialog: FragmentManager?
        get() = supportFragmentManager

    override val screenFragmentManager: FragmentManager?
        get() = supportFragmentManager

    open var statusbarColor: Int? = null

    override fun getParentFragment(): Fragment? {
        throw IllegalAccessError("Activity doesn't have parentFragment, If you want to use this method you should override this method and provide one")
    }

    override fun getChildFragmentManager(): FragmentManager {
        throw IllegalAccessError("Activity doesn't have childFragmentManager, If you want to use this method you should override this method and provide one")
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarBGColor(bgColor: Int?) {
        if (bgColor == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }

        window.statusBarColor = bgColor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(baseViews.layoutResId)

        // initialize SVC
        initializeSVC(this, baseViews, baseControlTower)

        val rootView: FrameLayout = window.decorView.findViewById(android.R.id.content)
        baseViews.rootView = rootView

        setStatusBarBGColor(statusbarColor)

        lifecycle.addObserver(baseViews)
        lifecycle.addObserver(baseControlTower)
        baseViews.changeIsFirstOnCreateFalse()
        baseControlTower.changeIsFirstOnCreateFalse()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(baseControlTower)
        lifecycle.removeObserver(baseViews)
    }

    override fun onBackPressed() {
        if (baseControlTower.onBackPressed() || baseViews.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }

    override val isActive: Boolean
        get() = !isFinishing
}
