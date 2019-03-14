package com.naver.android.svc.sample.dialog.listener

import com.naver.android.svc.core.views.Views
import com.naver.android.svc.sample.R
import kotlinx.android.synthetic.main.dialog_sample_action.view.*

/**
 * @author bs.nam@navercorp.com
 */
class SampleListenerViews : Views() {

    override val layoutResId = R.layout.dialog_sample_action

    /**
     * use casting just for reduce boiling plate code files and classes
     */
    private val dialog = screen as SampleListenerDialog
    private val dialogListener = dialog.dialogListener

    override fun onCreated() {
        rootView.btn_click.setOnClickListener {
            dialogListener.clickDialog()
            dialog.dismiss()
        }
    }

}