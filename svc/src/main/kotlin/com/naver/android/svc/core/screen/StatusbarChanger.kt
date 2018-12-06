package com.naver.android.svc.core.screen

import android.annotation.TargetApi
import android.os.Build
import android.view.Window

interface StatusbarChanger {
    /**
     * override get means the initial color
     * when you set statusbarColor you can change it
     */
    var statusbarColor: Int?
    fun getWindow(): Window

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarBGColor(bgColor: Int?) {
        if (bgColor == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }

        getWindow().statusBarColor = bgColor
    }
}