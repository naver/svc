package com.naver.android.svc.core.screen

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

open class SafeDialogFragment : DialogFragment() {

    override fun show(manager: FragmentManager?, tag: String?) {
        try {
            super.show(manager, tag)
        } catch (e: IllegalStateException) {
            manager?.beginTransaction()?.add(this, tag)?.commitAllowingStateLoss();
        }
    }
}