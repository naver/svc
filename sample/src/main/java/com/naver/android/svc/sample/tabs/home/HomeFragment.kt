package com.naver.android.svc.sample.tabs.home

import com.naver.android.svc.core.screen.SvcFragment
import com.naver.android.svc.sample.tabs.common.CommonViews

/**
 * @author bs.nam@navercorp.com
 */
class HomeFragment : SvcFragment<CommonViews, HomeControlTower>() {

    override fun createControlTower() = HomeControlTower(this, views)
    override fun createViews() = CommonViews()
}