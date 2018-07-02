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

import com.naver.android.svc.core.views.ActionViews
import com.naver.android.svc.sample.tabs.MainTab
import kotlinx.android.synthetic.main.activity_main.view.*

class MainViews : ActionViews<MainViewsAction>() {

    override val layoutResId = R.layout.activity_main

    override fun onCreated() {

        /**
         * case 1. you can call screen method directly with casting.
         * it's okay if you are using MainViews in one Screen (in this case MainActivity)
         * with casting you don't need to define useCase or make ControlTower.
         * If you think making useCase and ControlTower is waste of time (for simple screen) just cast screen like below
         * But not recommended way.
         */
        val mainActivity = screen as MainActivity
        rootView.gnb.onClickGnbListener = object : OnClickGnbListener {
            override fun onClickPaper() {
                mainActivity.changeScreen(MainTab.PAPER)
            }

            override fun onClickPalette() {
                mainActivity.changeScreen(MainTab.PALETTE)
            }

            override fun onClickSearch() {
                mainActivity.changeScreen(MainTab.SEARCH)
            }

            override fun onClickStatistic() {
                mainActivity.changeScreen(MainTab.STATISTIC)
            }
        }

        /**
         * case 2. when you want delegate event totally to ControlTower
         */
        rootView.gnb.onClickGnbListener = action

        /**
         * case 3. when you don't want to delegate to ControlTower for those events
         * in this case you don't need to make or define MainUseCase methods
         */
        rootView.gnb.onClickGnbListener = object : OnClickGnbListener {
            override fun onClickPaper() {
                //do stuff
                this@MainViews.onClickPaper()
            }

            override fun onClickPalette() {
                //do stuff
                this@MainViews.onClickPalette()
            }

            override fun onClickSearch() {
                //do stuff
                this@MainViews.onClickSearch()
            }

            override fun onClickStatistic() {
                //do stuff
                this@MainViews.onClickStatistic()
                //do stuff
            }
        }

        /**
         * case 4. when you have something to do in Views then call viewsAction
         */
        rootView.gnb.onClickGnbListener = object : OnClickGnbListener {
            override fun onClickPaper() {
                //do stuff
                action.onClickPaper()
            }

            override fun onClickPalette() {
                //do stuff
                action.onClickPalette()
            }

            override fun onClickSearch() {
                //do stuff
                action.onClickSearch()
            }

            override fun onClickStatistic() {
                //do stuff
                action.onClickStatistic()
            }
        }
    }

    private fun onClickPaper() {
        //do stuff
    }

    private fun onClickStatistic() {
        //do stuff
    }

    private fun onClickSearch() {
        //do stuff
    }

    private fun onClickPalette() {
        //do stuff
    }

}