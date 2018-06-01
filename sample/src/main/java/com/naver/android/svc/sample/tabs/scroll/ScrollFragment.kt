package com.naver.android.svc.sample.tabs.scroll

import com.naver.android.svc.core.screen.SvcFragment
import com.naver.android.svc.core.screen.SvcScreen
import com.naver.android.svc.sample.tabs.common.CommonCT
import com.naver.android.svc.sample.tabs.common.CommonViews

class ScrollFragment : SvcFragment<CommonViews, CommonCT>(), SvcScreen<CommonViews, CommonCT> {
    override fun createViews() = CommonViews(this)
    override fun createControlTower() = CommonCT(this, views)
}