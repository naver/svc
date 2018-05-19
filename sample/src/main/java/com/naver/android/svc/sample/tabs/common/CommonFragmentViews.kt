package com.naver.android.svc.sample.tabs.common

import com.naver.android.svc.sample.core.AbstractFragment
import com.naver.android.svc.core.views.UseCaseViews
import com.naver.android.svc.sample.R
import kotlinx.android.synthetic.main.fragment_common.*

class CommonFragmentViews(owner: AbstractFragment<*, *>) : UseCaseViews<AbstractFragment<*, *>, CommonUseCase>(owner) {

    override val layoutResId: Int
        get() = R.layout.fragment_common

    override fun onCreated() {
    }

    fun setExtraString(extra: String) {
        owner.extra.text = extra
    }
}