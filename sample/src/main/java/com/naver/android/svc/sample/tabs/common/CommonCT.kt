package com.naver.android.svc.sample.tabs.common

import com.naver.android.svc.core.SvcCT
import com.naver.android.svc.core.screen.SvcScreen

class CommonCT(screen: SvcScreen<CommonViews, *>, views: CommonViews) : SvcCT<SvcScreen<CommonViews, *>, CommonViews>(screen, views) {
    override fun onCreated() {
        views.setExtraString("CommonCT")
    }
}