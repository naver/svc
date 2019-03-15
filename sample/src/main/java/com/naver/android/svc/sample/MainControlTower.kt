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

package com.naver.android.svc.sample

import com.naver.android.annotation.ControlTower
import com.naver.android.svc.sample.tabs.MainTab

@ControlTower
class MainControlTower : SVC_MainActivityControlTower(), MainViewsAction {

    override fun onClickHome() {
        screen.changeScreen(MainTab.HOME)
    }

    override fun onClickPaper() {
        screen.changeScreen(MainTab.PAPER)
    }

    override fun onClickPalette() {
        screen.changeScreen(MainTab.PALETTE)
    }

    override fun onClickSearch() {
        screen.changeScreen(MainTab.SEARCH)
    }

    override fun onClickStatistic() {
        screen.changeScreen(MainTab.STATISTIC)
    }
}