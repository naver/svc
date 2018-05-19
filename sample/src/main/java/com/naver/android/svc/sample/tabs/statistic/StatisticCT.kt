package com.naver.android.svc.sample.tabs.statistic

import com.naver.android.svc.core.SvcBaseCT

class StatisticCT(owner: StatisticFragment, views: StatisticViews) : SvcBaseCT<StatisticFragment, StatisticViews>(owner, views) {
    override fun onCreated() {
        views.setName("St")
    }
}