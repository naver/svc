package com.naver.android.svc.sample.customview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater

abstract class CustomViewTemplate @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    abstract val layoutId: Int

    init {
        LayoutInflater.from(context).inflate(layoutId, this)
    }
}