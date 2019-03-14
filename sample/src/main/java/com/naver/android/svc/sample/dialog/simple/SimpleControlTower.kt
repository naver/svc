package com.naver.android.svc.sample.dialog.simple

import com.naver.android.svc.core.controltower.ControlTower

/**
 * @author bs.nam@navercorp.com
 */
class SimpleControlTower(screen: SimpleDialog, views: SimpleViews) : ControlTower<SimpleDialog, SimpleViews>(screen, views) {

    override fun onCreated() {
    }
}