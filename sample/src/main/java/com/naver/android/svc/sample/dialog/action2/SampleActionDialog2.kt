package com.naver.android.svc.sample.dialog.action2

import com.naver.android.svc.core.controltower.EmptyControlTower
import com.naver.android.svc.core.screen.SvcDialogFragment
import com.naver.android.svc.sample.dialog.action1.SampleActionViews2

/**
 * @author bs.nam@navercorp.com
 */
class SampleActionDialog2 : SvcDialogFragment<SampleActionViews2, EmptyControlTower<*,*>, SampleActionDialogListener2>() {

    override fun createControlTower() = EmptyControlTower(this, views)
    override fun createViews() = SampleActionViews2()

    companion object {
        fun newInstance(listener: SampleActionDialogListener2): SampleActionDialog2 {
            val dialog = SampleActionDialog2()
            dialog.dialogListener = listener
            return dialog
        }
    }
}

interface SampleActionDialogListener2 {
    fun clickDialog()
}