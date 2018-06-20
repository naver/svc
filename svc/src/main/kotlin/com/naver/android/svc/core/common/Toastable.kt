package com.naver.android.svc.core.common

import android.content.Context
import android.widget.Toast

interface Toastable {
    val context: Context?

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }
}