package com.naver.android.svc.sample.dialog.action

import com.naver.android.svc.core.controltower.ControlTower

/**
 * If you think this SampleActionControlTower is just passing events and doing nothing
 * just remove this useless ControlTower And ViewsAction.
 * Check: SampleListenerDialog
 *
 * @see com.naver.android.svc.sample.dialog.listener.SampleListenerDialog
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

         */
    }
}