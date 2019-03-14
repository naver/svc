package com.naver.android.svc.sample.dialog.action1

import com.naver.android.svc.core.views.Views
import com.naver.android.svc.sample.R
import com.naver.android.svc.sample.dialog.action2.SampleActionDialog2
import kotlinx.android.synthetic.main.dialog_sample_action.view.*

/**
 * @author bs.nam@navercorp.com
 */
class SampleActionViews2 : Views() {

    override val layoutResId = R.layout.dialog_sample_action

    override fun onCreated() {
        rootView.btn_click.setOnClickListener {
            (screen as SampleActionDialog2).dialogListener.clickDialog()
            (screen as SampleActionDialog2).dismiss()
        }
    }
}