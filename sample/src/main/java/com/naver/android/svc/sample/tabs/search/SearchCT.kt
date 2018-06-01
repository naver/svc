package com.naver.android.svc.sample.tabs.search

import com.naver.android.svc.core.SvcCT
import com.naver.android.svc.sample.tabs.common.CommonViews

class SearchCT(screen: SearchFragment, views: CommonViews) : SvcCT<SearchFragment, CommonViews>(screen, views) {
    override fun onCreated() {
        views.setExtraString("SearchCT")
    }
}