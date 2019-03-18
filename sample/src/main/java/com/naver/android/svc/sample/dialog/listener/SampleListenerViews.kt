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
    private val dialog by lazy { screen as SampleListenerDialog }
    private val dialogListener by lazy { dialog.dialogListener }

    override fun onCreated() {
        rootView.btn_click.setOnClickListener {
            dialogListener.clickDialog()
            dialog.dismiss()
        }
    }
}
