package com.naver.android.svc.sample.tabs.home

import com.naver.android.svc.core.qualifiers.RequireControlTower
import com.naver.android.svc.core.screen.SvcFragment
import com.naver.android.svc.sample.tabs.common.CommonViews

/**
 * @author bs.nam@navercorp.com
 */
@RequireControlTower(HomeControlTower::class)
class HomeFragment : SvcFragment<CommonViews>() {

    override fun createViews() = CommonViews()
}