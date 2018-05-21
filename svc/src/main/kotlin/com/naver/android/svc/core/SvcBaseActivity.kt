package com.naver.android.svc.core

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.naver.android.svc.core.views.SvcBaseViews
import com.naver.android.svc.core.views.UseCase
import com.naver.android.svc.core.views.UseCaseViews


/**
 * @author bs.nam@navercorp.com 2017. 6. 8..
 */

abstract class SvcBaseActivity<out V : SvcBaseViews<*>, out C : SvcBaseCT<*, *>> : AppCompatActivity(), ActivityProvider {

    var CLASS_SIMPLE_NAME = javaClass.simpleName
    val TAG: String = CLASS_SIMPLE_NAME

    val views by lazy { createViews() }
    val ct by lazy { createControlTower() }
    var statusbarColor: Int? = null

    protected abstract fun createViews(): V
    protected abstract fun createControlTower(): C

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

        if (finalViews is UseCaseViews<*, *> && finalController is UseCase) {
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

    override fun getActivity(): FragmentActivity? {
        return this
    }

    override fun onBackPressed() {
        if (ct.onBackPressed() || views.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }

    fun isAvailable(): Boolean {
        return !isFinishing
    }
}
