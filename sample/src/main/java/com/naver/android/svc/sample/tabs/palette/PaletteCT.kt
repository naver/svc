package com.naver.android.svc.sample.tabs.palette

import android.util.Log
import com.naver.android.svc.core.SvcBaseCT
import com.naver.android.svc.sample.tabs.common.CommonFragmentViews

class PaletteCT(owner: PaletteFragment, views: CommonFragmentViews) : SvcBaseCT<PaletteFragment, CommonFragmentViews>(owner, views) {
    override fun onCreated() {
        views.setExtraString("PaletteCT")
    }

    override fun onStop() {
        Log.d(TAG, "override onStop")
        super.onStop()
    }

}