package com.naver.android.svc.sample.tabs.common

import com.naver.android.svc.core.SvcBaseCT
import com.naver.android.svc.sample.core.AbstractFragment
import com.naver.android.svc.sample.tabs.common.CommonFragmentViews

class CommonCT(owner: AbstractFragment<*, *>, views: CommonFragmentViews) : SvcBaseCT<AbstractFragment<*, *>, CommonFragmentViews>(owner, views) {
    override fun onCreated() {
        views.setExtraString("CommonCT")
    }
}