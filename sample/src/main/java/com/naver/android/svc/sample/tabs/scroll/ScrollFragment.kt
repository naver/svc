package com.naver.android.svc.sample.tabs.scroll

import com.naver.android.svc.sample.core.AbstractFragment
import com.naver.android.svc.sample.tabs.common.CommonCT
import com.naver.android.svc.sample.tabs.common.CommonFragmentViews

class ScrollFragment : AbstractFragment<CommonFragmentViews, CommonCT>() {
    override fun createViews() = CommonFragmentViews(this)
    override fun createControlTower() = CommonCT(this, views)
}