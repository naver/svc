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

import com.naver.android.annotation.ControlTower
import com.naver.android.annotation.RequireScreen
import com.naver.android.annotation.RequireViews
import com.naver.android.svc.sample.tabs.common.CommonViews
import com.naver.android.svc.sample.tabs.common.CommonViewsAction

/**
 * if your controlTower class name is too long,
 * maybe you should Abbreviate name as "CT"
 */
@ControlTower
@RequireViews(CommonViews::class)
@RequireScreen(ReallyLongScreenNameSearchFragment::class)
class ReallyLongScreenNameSearchCT : SVC_ReallyLongScreenNameSearchCT(), CommonViewsAction {

  override fun onCreated() {
    views.setExtraString("ReallyLongScreenNameSearchCT")
  }

  override fun onClickBtn() {
    showToast("ReallyLongScreenNameSearchCT - onClickBtn")
  }
}
