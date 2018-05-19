package com.naver.android.svc.sample.tabs.search

import com.naver.android.svc.core.SvcBaseCT
import com.naver.android.svc.sample.tabs.common.CommonFragmentViews

class SearchCT(owner: SearchFragment, views: CommonFragmentViews) : SvcBaseCT<SearchFragment, CommonFragmentViews>(owner, views) {
    override fun onCreated() {
        views.setExtraString("SearchCT")
    }
}