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

import com.naver.android.svc.annotation.RequireControlTower
import com.naver.android.svc.annotation.RequireListener
import com.naver.android.svc.annotation.RequireViews
import com.naver.android.svc.annotation.SvcDialogFragment
import com.naver.android.svc.core.controltower.EmptyControlTower

/**
 * @author bs.nam@navercorp.com
 */
@SvcDialogFragment
@RequireViews(SampleListenerViews::class)
@RequireControlTower(EmptyControlTower::class)
@RequireListener(SampleListenerDialogListener::class)
class SampleListenerDialog : SVC_SampleListenerDialog() {

    companion object {
        fun newInstance(listener: SampleListenerDialogListener): SampleListenerDialog {
            val dialog = SampleListenerDialog()
            dialog.dialogListener = listener
            return dialog
        }
    }
}

/**
 * name is weired :(
 */
interface SampleListenerDialogListener {
    fun clickDialog()
}
