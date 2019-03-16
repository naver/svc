package com.naver.android.svc.sample.dialog.action

import com.naver.android.annotation.RequireScreen
import com.naver.android.annotation.RequireViews

/**
 * If you think this SampleActionControlTower is just passing events and doing nothing
 * just remove this useless ControlTower And ViewsAction.
 * Check: SampleListenerDialog
 *
 * @see com.naver.android.svc.sample.dialog.listener.SampleListenerDialog
 * @author bs.nam@navercorp.com
 */
@com.naver.android.annotation.ControlTower
@RequireScreen(SampleActionDialog::class)
@RequireViews(SampleActionViews::class)
class SampleActionControlTower : SVC_SampleActionControlTower(), SampleActionViewsAction {

    override fun onCreated() {
    }

    override fun onClickButton() {
        //do something
        screen.dialogListener.clickDialog()
        screen.dismiss()
    }
}