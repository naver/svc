package com.naver.android.svc.sample.tabs.common

import com.naver.android.svc.core.screen.SvcScreen
import com.naver.android.svc.core.views.UseCaseViews
import com.naver.android.svc.sample.R
import kotlinx.android.synthetic.main.fragment_common.view.*

class CommonViews(screen: SvcScreen<*, *>) : UseCaseViews<SvcScreen<*, *>, CommonUseCase>(screen) {

    override val layoutResId: Int
        get() = R.layout.fragment_common

    override fun onCreated() {
    }

    fun setExtraString(extra: String) {
        val rootView = rootView ?: return
        rootView.extra.text = extra
    }
}