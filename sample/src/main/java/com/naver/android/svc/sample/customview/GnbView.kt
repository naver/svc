/*
 * Copyright 2018 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.naver.android.svc.sample.customview

import android.content.Context
import android.util.AttributeSet
import com.naver.android.svc.sample.OnClickGnbListener
import com.naver.android.svc.sample.R
import kotlinx.android.synthetic.main.v_gnb.view.*

class GnbView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CustomViewTemplate(context, attrs, defStyleAttr) {

    override val layoutId
        get() = R.layout.v_gnb

    var onClickGnbListener: OnClickGnbListener? = null

    init {
        gnb_home.setOnClickListener {
            onClickGnbListener?.onClickHome()
        }

        gnb_palete.setOnClickListener {
            onClickGnbListener?.onClickPalette()
        }

        gnb_search.setOnClickListener {
            onClickGnbListener?.onClickSearch()
        }

        gnb_statistic.setOnClickListener {
            onClickGnbListener?.onClickStatistic()
        }

        gnb_paper.setOnClickListener {
            onClickGnbListener?.onClickPaper()
        }
    }
}
