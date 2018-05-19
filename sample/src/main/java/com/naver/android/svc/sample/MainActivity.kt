package com.naver.android.svc.sample

import com.naver.android.svc.core.SvcBaseActivity

class MainActivity : SvcBaseActivity<MainViews, MainCT>() {
    override fun createViews() = MainViews(this)
    override fun createControlTower() = MainCT(this, views)
}
