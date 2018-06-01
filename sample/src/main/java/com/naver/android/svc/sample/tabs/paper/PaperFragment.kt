package com.naver.android.svc.sample.tabs.paper

import com.naver.android.svc.core.screen.SvcFragment
import com.naver.android.svc.sample.tabs.common.CommonCT
import com.naver.android.svc.sample.tabs.common.CommonViews

class PaperFragment : SvcFragment<CommonViews, CommonCT>() {
    override fun createViews() = CommonViews(this)
    override fun createControlTower() = CommonCT(this, views)
}