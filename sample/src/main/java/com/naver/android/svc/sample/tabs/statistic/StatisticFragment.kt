package com.naver.android.svc.sample.tabs.statistic

import com.naver.android.svc.core.screen.SvcFragment

class StatisticFragment : SvcFragment<StatisticViews, StatisticCT>() {
    override fun createViews() = StatisticViews(this)
    override fun createControlTower() = StatisticCT(this, views)
}