package com.naver.android.svc.sample.tabs.home

import com.naver.android.svc.core.controltower.ControlTower
import com.naver.android.svc.sample.tabs.common.CommonViews
import com.naver.android.svc.sample.tabs.common.CommonViewsAction

/**
 * @author bs.nam@navercorp.com
 */
class HomeControlTower(screen: HomeFragment, views: CommonViews) : ControlTower<HomeFragment, CommonViews>(screen, views), CommonViewsAction {

    override fun onCreated() {
        views.setExtraString("HomeControlTower")
    }
}