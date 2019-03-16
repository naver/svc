package com.naver.android.svc.sample.dialog.action

import com.naver.android.svc.core.views.ActionViews
import com.naver.android.svc.sample.R
import kotlinx.android.synthetic.main.dialog_sample_action.view.*

/**
 * @author bs.nam@navercorp.com
 */
class SampleActionViews : ActionViews<SampleActionViewsAction>() {

    override val layoutResId = R.layout.dialog_sample_action

    override fun onCreated() {
        rootView.btn_click.setOnClickListener {
            viewsAction.onClickButton()
        }
    }
}