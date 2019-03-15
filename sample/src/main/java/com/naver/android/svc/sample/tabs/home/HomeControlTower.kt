package com.naver.android.svc.sample.tabs.home

import com.naver.android.annotation.ControlTower
import com.naver.android.svc.sample.tabs.common.CommonViewsAction

/**
 * @author bs.nam@navercorp.com
 */
@ControlTower
class HomeControlTower : SVC_HomeFragmentControlTower(), CommonViewsAction {

    override fun onCreated() {
        view.setExtraString("HomeControlTower")
    }
}