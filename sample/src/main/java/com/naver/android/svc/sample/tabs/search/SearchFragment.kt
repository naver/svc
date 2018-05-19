package com.naver.android.svc.sample.tabs.search

import com.naver.android.svc.sample.core.AbstractFragment
import com.naver.android.svc.sample.tabs.common.CommonFragmentViews

class SearchFragment : AbstractFragment<CommonFragmentViews, SearchCT>() {
    override fun createViews() = CommonFragmentViews(this)
    override fun createControlTower() = SearchCT(this, views)
}