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
package com.naver.android.svc.sample.tabs.common

import com.naver.android.svc.annotation.ControlTower
import com.naver.android.svc.annotation.RequireScreen
import com.naver.android.svc.annotation.RequireViews
import com.naver.android.svc.core.screen.DialogSupportScreen
import com.naver.android.svc.sample.dialog.listener.SampleListenerDialog
import com.naver.android.svc.sample.dialog.listener.SampleListenerDialogListener

@ControlTower
@RequireViews(CommonViews::class)
@RequireScreen(CommonScreen::class)
class CommonControlTower : SVC_CommonControlTower(), CommonViewsAction {

    private var isToggled = false

    override fun onCreated() {
        views.setNameText(screen.javaClass.simpleName)
        views.setExtraText("Open SampleListenerDialog")
        views.setButtonText("Start CommonActivity")
    }

    override fun onClickBtn() {
        screen.startCommonActivity()
    }

    override fun onClickExtra() {
        val dialog = SampleListenerDialog.newInstance(object : SampleListenerDialogListener {
            override fun clickDialog() {
                views.setExtraText(isToggled.toString())
                isToggled = !isToggled
            }
        })

        (screen as? DialogSupportScreen)?.showDialog(dialog)
    }
}
