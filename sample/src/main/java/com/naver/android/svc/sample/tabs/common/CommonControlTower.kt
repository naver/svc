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

import com.naver.android.annotation.ControlTower
import com.naver.android.annotation.RequireScreen
import com.naver.android.annotation.RequireViews

@ControlTower
@RequireViews(CommonViews::class)
@RequireScreen(CommonScreen::class)
class CommonControlTower : SVC_CommonControlTower(), CommonViewsAction {

  override fun onCreated() {
    views.setExtraString("CommonControlTower")
    views.setButtonText("Start CommonActivity")
  }

  override fun onClickBtn() {
    screen.startCommonActivity()
  }
}
