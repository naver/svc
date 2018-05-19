package com.naver.android.svc.sample.customview

import android.content.Context
import android.util.AttributeSet
import com.naver.android.svc.sample.core.OnClickGnbListener
import com.naver.android.svc.sample.R
import kotlinx.android.synthetic.main.v_gnb.view.*

class GnbView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomViewTemplate(context, attrs, defStyleAttr) {

    override val layoutId
        get() = R.layout.v_gnb

    var onClickGnbListener: OnClickGnbListener? = null

    init {
        gnb_palete.setOnClickListener {
            onClickGnbListener?.onClickPalette()
        }

        gnb_search.setOnClickListener {
            onClickGnbListener?.onClickSearch()
        }

        gnb_statistic.setOnClickListener {
            onClickGnbListener?.onClickStatistic()
        }

        gnb_scroll.setOnClickListener {
            onClickGnbListener?.onClickScroll()
        }
    }
}