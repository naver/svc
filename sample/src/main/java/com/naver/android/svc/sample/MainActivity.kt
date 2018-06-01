package com.naver.android.svc.sample

import com.naver.android.svc.core.screen.SvcActivity

class MainActivity : SvcActivity<MainViews, MainCT>() {
    override fun createViews() = MainViews(this)
    override fun createControlTower() = MainCT(this, views)
}
