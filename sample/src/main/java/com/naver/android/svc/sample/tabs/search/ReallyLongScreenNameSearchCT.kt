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

package com.naver.android.svc.sample.tabs.search

import com.naver.android.svc.core.controltower.ControlTower
import com.naver.android.svc.core.screen.DialogSupportScreen
import com.naver.android.svc.sample.dialog.action2.SampleActionDialog2
import com.naver.android.svc.sample.dialog.action2.SampleActionDialogListener2
import com.naver.android.svc.sample.tabs.common.CommonViews
import com.naver.android.svc.sample.tabs.common.CommonViewsAction

/**
 * if your controlTower class name is too long,
 * maybe you should Abbreviate name as "CT"
 */
class ReallyLongScreenNameSearchCT(screen: ReallyLongScreenNameSearchFragment, views: CommonViews) : ControlTower<ReallyLongScreenNameSearchFragment, CommonViews>(screen, views),
        CommonViewsAction {

    private var isToggled = false

    override fun onCreated() {
        views.setExtraString("SampleActionDialog2")
    }

    override fun onClickExtra() {
        val dialog = SampleActionDialog2.newInstance(object : SampleActionDialogListener2 {
            override fun clickDialog() {
                views.setExtraString(isToggled.toString())
                isToggled = !isToggled
            }
        })

        (screen as? DialogSupportScreen)?.showDialog(dialog)
    }
}