package com.naver.android.svc.sample.tabs.statistic

import com.naver.android.svc.core.SvcCT

class StatisticCT(screen: StatisticFragment, views: StatisticViews) : SvcCT<StatisticFragment, StatisticViews>(screen, views) {
    override fun onCreated() {
        views.setName("St")
    }
}