package com.naver.android.svc.core

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.naver.android.svc.BuildConfig
import com.naver.android.svc.core.views.SvcBaseViews
import com.naver.android.svc.core.views.UseCase
import com.naver.android.svc.core.views.UseCaseViews

/**
 * @author bs.nam@navercorp.com 2017. 6. 8..
 */

abstract class SvcBaseFragment<out V : SvcBaseViews<*>, out C : SvcBaseCT<*, *>> : Fragment(), ActivityProvider {

    val CLASS_SIMPLE_NAME = javaClass.simpleName
    var TAG: String = CLASS_SIMPLE_NAME

    companion object {
        const val EXTRA_TAG_ID = BuildConfig.APPLICATION_ID + ".EXTRA_TAG_ID"
    }

    val views by lazy { createViews() }
    val ct by lazy { createControlTower() }

    protected abstract fun createViews(): V
    protected abstract fun createControlTower(): C

    override fun onCreate(savedInstanceState: Bundle?) {
        addExtraTagId()
        super.onCreate(savedInstanceState)
    }

    private fun addExtraTagId() {
        val extraId = arguments?.getString(EXTRA_TAG_ID)
        extraId?.apply {
            ct.TAG = "${ct.CLASS_SIMPLE_NAME}_$this"
            views.TAG = "${views.CLASS_SIMPLE_NAME}_$this"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (views.isInitialized) views.rootView
        else inflater.inflate(views.layoutResId, container, false) as ViewGroup?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val finalViews = views
        val finalController = ct

        if (finalViews is UseCaseViews<*, *> && finalController is UseCase) {
            finalViews.setControllerUsecase(finalController)
        }

        if (!views.isInitialized) {
            views.rootView = view as ViewGroup
        }

        lifecycle.addObserver(views)
        lifecycle.addObserver(ct)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(ct)
        lifecycle.removeObserver(views)
    }

    fun isAvailable(): Boolean {
        return activity != null && context != null && isAdded && !isRemoving && !isDetached
    }
}
