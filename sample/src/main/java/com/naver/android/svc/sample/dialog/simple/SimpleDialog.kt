package com.naver.android.svc.sample.dialog.simple

import com.naver.android.annotation.RequireControlTower
import com.naver.android.annotation.RequireListener
import com.naver.android.annotation.RequireViews
import com.naver.android.annotation.SvcDialogFragment

/**
 * @author bs.nam@navercorp.com
 */
@SvcDialogFragment
@RequireViews(SimpleViews::class)
@RequireControlTower(SimpleControlTower::class)
@RequireListener(Unit::class)
class SimpleDialog : SVC_SimpleDialog() {

    companion object {
        fun newInstance(): SimpleDialog {
            val dialog = SimpleDialog()
            dialog.dialogListener = Unit
            return dialog
        }
    }
}