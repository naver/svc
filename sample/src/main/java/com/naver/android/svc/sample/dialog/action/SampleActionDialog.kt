package com.naver.android.svc.sample.dialog.action

import com.naver.android.svc.core.screen.SvcDialogFragment

/**
 * @author bs.nam@navercorp.com
 */
class SampleActionDialog : SvcDialogFragment<SampleActionViews, SampleActionControlTower, SampleActionDialogListener>() {

    override fun createControlTower() = SampleActionControlTower(this, views)
    override fun createViews() = SampleActionViews()

    companion object {
        fun newInstance(listener: SampleActionDialogListener): SampleActionDialog {
            val dialog = SampleActionDialog()
            dialog.dialogListener = listener
            return dialog
        }
    }
}

interface SampleActionDialogListener {
    fun clickDialog()
}