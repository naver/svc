package com.naver.android.svc.sample.dialog.listener

import com.naver.android.svc.core.controltower.EmptyControlTower
import com.naver.android.svc.core.screen.SvcDialogFragment

/**
 * @author bs.nam@navercorp.com
 */
class SampleListenerDialog : SvcDialogFragment<SampleListenerViews, EmptyControlTower<*,*>, SampleListenerDialogListener>() {

    override fun createControlTower() = EmptyControlTower(this, views)
    override fun createViews() = SampleListenerViews()

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