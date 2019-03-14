package com.naver.android.svc.sample.dialog.action1

import com.naver.android.svc.core.controltower.ControlTower

/**
 * @author bs.nam@navercorp.com
 */
class SampleActionControlTower(screen: SampleActionDialog, views: SampleActionViews) : ControlTower<SampleActionDialog, SampleActionViews>(screen, views), SampleActionViewsAction {

    override fun onCreated() {
    }

    override fun onClickButton() {
        //do something
        screen.dialogListener.clickDialog()
        screen.dismiss()

        /**
         * if you think this SampleActionControlTower is just pass the event and doing nothing.
         * Just remove this useless ControlTower And Views
         * Look At SampleActionDialog2
         */
    }
}