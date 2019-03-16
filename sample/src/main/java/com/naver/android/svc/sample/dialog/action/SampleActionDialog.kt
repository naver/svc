package com.naver.android.svc.sample.dialog.action

import com.naver.android.annotation.RequireControlTower
import com.naver.android.annotation.RequireListener
import com.naver.android.annotation.RequireViews

/**
 * @author bs.nam@navercorp.com
 */
@com.naver.android.annotation.SvcDialogFragment
@RequireViews(SampleActionViews::class)
@RequireControlTower(SampleActionControlTower::class)
@RequireListener(SampleActionDialogListener::class)
class SampleActionDialog : SVC_SampleActionDialog() {

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