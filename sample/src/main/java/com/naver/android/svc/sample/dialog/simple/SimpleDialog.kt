package com.naver.android.svc.sample.dialog.simple

import com.naver.android.svc.core.screen.SvcDialogFragment

/**
 * @author bs.nam@navercorp.com
 */
class SimpleDialog : SvcDialogFragment<SimpleViews, SimpleControlTower, Unit>() {

    override fun createControlTower() = SimpleControlTower(this, views)
    override fun createViews() = SimpleViews()

    companion object {
        fun newInstance(): SimpleDialog {
            val dialog = SimpleDialog()
            dialog.dialogListener = Unit
            return dialog
        }
    }
}