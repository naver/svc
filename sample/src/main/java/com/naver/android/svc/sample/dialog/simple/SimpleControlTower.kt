package com.naver.android.svc.sample.dialog.simple

import com.naver.android.annotation.RequireScreen
import com.naver.android.annotation.RequireViews

/**
 * @author bs.nam@navercorp.com
 */
@com.naver.android.annotation.ControlTower
@RequireScreen(SimpleDialog::class)
@RequireViews(SimpleViews::class)
class SimpleControlTower : SVC_SimpleControlTower() {

    override fun onCreated() {
    }
}