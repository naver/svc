package com.naver.android.svc.sample.dialog.listener

import com.naver.android.annotation.RequireControlTower
import com.naver.android.annotation.RequireListener
import com.naver.android.annotation.RequireViews
import com.naver.android.svc.core.controltower.EmptyControlTower

/**
 * @author bs.nam@navercorp.com
 */
@com.naver.android.annotation.SvcDialogFragment
@RequireViews(SampleListenerViews::class)
@RequireControlTower(EmptyControlTower::class)
@RequireListener(SampleListenerDialogListener::class)
class SampleListenerDialog : SVC_SampleListenerDialog() {

    companion object {
        fun newInstance(listener: SampleListenerDialogListener): SampleListenerDialog {
            val dialog = SampleListenerDialog()
            dialog.dialogListener = listener
            return dialog
        }
    }
}

/**
 * name is weired :(
 */
interface SampleListenerDialogListener {
    fun clickDialog()
}