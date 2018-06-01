package com.naver.android.svc.core.screen

import android.app.Dialog
import android.arch.lifecycle.LifecycleOwner
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.naver.android.svc.core.SvcCT
import com.naver.android.svc.core.views.SvcViews
import com.naver.android.svc.core.views.UseCase
import com.naver.android.svc.core.views.UseCaseViews


/**
 * @author bs.nam@navercorp.com 2017. 11. 22..
 */
abstract class SvcDialogFragment<out V : SvcViews<*>, C : SvcCT<*, *>> : DialogFragment(), LifecycleOwner, SvcScreen<V, C> {

    val CLASS_SIMPLE_NAME = javaClass.simpleName
    var TAG: String = CLASS_SIMPLE_NAME

    override val views by lazy { createViews() }
    override val ct by lazy { createControlTower() }
    override val hostActivity: FragmentActivity?
        get() = activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        views.rootView?.setOnClickListener {
            dismissAllowingStateLoss()
        }

        return views.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val finalViews = views
        val finalController = ct
        if (finalViews is UseCaseViews<*, *> && finalController is UseCase) {
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
        views.rootView = LayoutInflater.from(context).inflate(views.layoutResId, null) as ViewGroup?
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

    fun isAvailable(): Boolean {
        return hostActivity != null && context != null && isAdded && !isRemoving && !isDetached
    }
}
