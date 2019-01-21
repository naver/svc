package com.naver.android.svc.sample.dialog

import com.naver.android.svc.core.views.Views
import com.naver.android.svc.sample.R
import kotlinx.android.synthetic.main.dialog_sample.view.*

class SampleDialogViews : Views() {
    override val layoutResId: Int = R.layout.dialog_sample

    override fun onCreated() {
        rootView.tv_dialog.setOnClickListener { (screen as SampleDialog).dialogListener.onClickText() }
    }
}