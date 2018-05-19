package com.naver.android.svc.sample.tabs.palette

import com.naver.android.svc.sample.core.AbstractFragment
import com.naver.android.svc.sample.tabs.common.CommonFragmentViews

class PaletteFragment : AbstractFragment<CommonFragmentViews, PaletteCT>() {
    override fun createViews() = CommonFragmentViews(this)
    override fun createControlTower() = PaletteCT(this, views)
}