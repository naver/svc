package com.naver.android.svc.sample.dialog

import com.naver.android.svc.core.controltower.EmptyControlTower
import com.naver.android.svc.core.screen.SvcDialogFragment

class SampleDialog : SvcDialogFragment<SampleDialogViews, EmptyControlTower<*, *>, SampleDialogListener>() {
    override fun createViews() = SampleDialogViews()
    override fun createControlTower() = EmptyControlTower(this, views)

    companion object {
        fun newInstance(listener: SampleDialogListener): SampleDialog {
            return SampleDialog().apply { dialogListener = listener }
        }
    }
}


interface SampleDialogListener {
    fun onClickText()
}

