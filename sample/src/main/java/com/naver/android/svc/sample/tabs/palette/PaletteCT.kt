package com.naver.android.svc.sample.tabs.palette

import android.util.Log
import com.naver.android.svc.core.SvcCT
import com.naver.android.svc.sample.tabs.common.CommonViews

class PaletteCT(screen: PaletteFragment, views: CommonViews) : SvcCT<PaletteFragment, CommonViews>(screen, views) {
    override fun onCreated() {
        views.setExtraString("PaletteCT")
    }

    override fun onStop() {
        Log.d(TAG, "override onStop")
        super.onStop()
    }

}