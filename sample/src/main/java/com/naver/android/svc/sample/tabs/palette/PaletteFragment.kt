package com.naver.android.svc.sample.tabs.palette

import com.naver.android.svc.core.screen.SvcFragment
import com.naver.android.svc.sample.tabs.common.CommonViews

class PaletteFragment : SvcFragment<CommonViews, PaletteCT>() {
    override fun createViews() = CommonViews(this)
    override fun createControlTower() = PaletteCT(this, views)
}