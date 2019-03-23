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
package com.naver.android.svc.sample.tabs.statistic

import com.naver.android.svc.annotation.ControlTower
import com.naver.android.svc.annotation.RequireScreen
import com.naver.android.svc.annotation.RequireViews

@ControlTower
@RequireViews(StatisticViews::class)
@RequireScreen(StatisticFragment::class)
class StatisticControlTower : SVC_StatisticControlTower(), StatisticViewsAction {

    override fun onCreated() {
        views.setName(screen.javaClass.simpleName)
        views.setExtra(javaClass.simpleName)
    }

    override fun onClickName() {
        showToast("StatisticControlTower - Name Clicked")
    }

    override fun onClickExtra() {
        showToast("StatisticControlTower - Extra Clicked")
    }
}
