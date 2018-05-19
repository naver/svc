package com.naver.android.svc.sample.tabs.statistic

import com.naver.android.svc.core.views.UseCaseViews
import com.naver.android.svc.sample.R
import kotlinx.android.synthetic.main.fragment_statistic.*

class StatisticViews(owner: StatisticFragment) : UseCaseViews<StatisticFragment, StatisticUseCase>(owner) {
    override val layoutResId: Int
        get() = R.layout.fragment_statistic

    override fun onCreated() {
    }

    fun setName(name: String) {
        owner.name.text = name
    }
}