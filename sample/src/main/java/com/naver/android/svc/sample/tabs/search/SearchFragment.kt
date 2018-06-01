package com.naver.android.svc.sample.tabs.search

import com.naver.android.svc.core.screen.SvcFragment
import com.naver.android.svc.core.screen.SvcScreen
import com.naver.android.svc.sample.tabs.common.CommonViews

class SearchFragment : SvcFragment<CommonViews, SearchCT>(), SvcScreen<CommonViews, SearchCT> {
    override fun createViews() = CommonViews(this)
    override fun createControlTower() = SearchCT(this, views)
}