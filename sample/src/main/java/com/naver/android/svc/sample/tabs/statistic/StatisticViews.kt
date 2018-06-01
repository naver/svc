package com.naver.android.svc.sample.tabs.statistic

import com.naver.android.svc.core.views.UseCaseViews
import com.naver.android.svc.sample.R
import kotlinx.android.synthetic.main.fragment_statistic.*

class StatisticViews(screen: StatisticFragment) : UseCaseViews<StatisticFragment, StatisticUseCase>(screen) {
    override val layoutResId: Int
        get() = R.layout.fragment_statistic

    override fun onCreated() {
    }

    fun setName(name: String) {
        screen.name.text = name
    }
}