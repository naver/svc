package com.naver.android.svc.sample.tabs.home

import com.naver.android.svc.sample.tabs.common.CommonViewsAction

/**
 * @author bs.nam@navercorp.com
 */
class HomeControlTower : SVC_HomeFragmentControlTower(), CommonViewsAction {

    override fun onCreated() {
        view.setExtraString("HomeControlTower")
    }
}