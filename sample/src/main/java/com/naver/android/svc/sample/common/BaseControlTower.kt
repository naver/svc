package com.naver.android.svc.sample.common

import androidx.annotation.CallSuper
import com.naver.android.svc.core.controltower.ControlTower

/**
 * @author bs.nam@navercorp.com
 */
abstract class BaseControlTower: ControlTower(){
    @CallSuper
    override fun onCreated() {
        super.onCreated()
        showToast("Toast from BaseControlTower")
    }
}