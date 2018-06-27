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

import android.app.Dialog
import android.arch.lifecycle.LifecycleOwner
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.naver.android.svc.core.controltower.SvcCT
import com.naver.android.svc.core.views.SvcViews
import com.naver.android.svc.core.views.UseCase
import com.naver.android.svc.core.views.UseCaseViews


/**
 * you should set dialogListener after you create dialog instance.
 * if your dialog has no interaction set Unit.INSTANCE at "dialogListener" field
 * @author bs.nam@navercorp.com 2017. 11. 22..
 */
abstract class SvcDialogFragment<out V : SvcViews, out C : SvcCT<*, *>, DL : Any> : DialogFragment(), LifecycleOwner, SvcScreen<V, C> {

    val CLASS_SIMPLE_NAME = javaClass.simpleName
    var TAG: String = CLASS_SIMPLE_NAME

    override val views by lazy { createViews() }
    override val ct by lazy { createControlTower() }

    override val hostActivity: FragmentActivity?
        get() = activity

    override val screenFragmentManager: FragmentManager?
        get() = fragmentManager

    lateinit var dialogListener: DL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (::dialogListener.isInitialized) {
            dismissAllowingStateLoss()
            return
        }

        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        views.rootView.setOnClickListener {
            dismissAllowingStateLoss()
        }

        return views.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val finalViews = views
        val finalController = ct
        if (finalViews is UseCaseViews<*> && finalController is UseCase) {
            finalViews.setControllerUsecase(finalController)
        }

        lifecycle.addObserver(views)
        lifecycle.addObserver(ct)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(ct)
        lifecycle.removeObserver(views)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        views.rootView = LayoutInflater.from(context).inflate(views.layoutResId, null) as ViewGroup
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnKeyListener { _, keyCode, _ ->

            if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
                dismissAllowingStateLoss()
                true
            } else {
                false

            }
        }

        return dialog
    }

    override fun dismiss() {
        dismissAllowingStateLoss()
    }

    override val isActive: Boolean
        get() = hostActivity != null && context != null && isAdded && !isRemoving && !isDetached

}
