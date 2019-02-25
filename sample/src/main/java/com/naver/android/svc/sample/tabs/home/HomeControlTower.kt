package com.naver.android.svc.sample.tabs.home

import com.naver.android.svc.core.controltower.ControlTower
import com.naver.android.svc.sample.tabs.common.CommonViews
import com.naver.android.svc.sample.tabs.common.CommonViewsAction

/**
 * @author bs.nam@navercorp.com
 */
class HomeControlTower : ControlTower(), CommonViewsAction {

    private lateinit var commonViews: CommonViews

    override fun onCreated() {
        this.commonViews = getViews()

        this.commonViews.setExtraString("HomeControlTower")
    }
}