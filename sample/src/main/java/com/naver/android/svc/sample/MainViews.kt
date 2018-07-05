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
import kotlinx.android.synthetic.main.activity_main.view.*

class MainViews : ActionViews<MainViewsAction>() {

    override val layoutResId = R.layout.activity_main

    override fun onCreated() {
        /**
         * case 1. when you want delegate event totally to CT
         */
        withRootView {
            gnb.onClickGnbListener = viewsAction
        }
        //screen.gnb.onClickGnbListener = useCase

        /**
         * case 2. when you don't want to delegate to CT for those events
         * in this case you don't need to define MainUseCase methods
         */
        rootView.gnb?.onClickGnbListener = object : OnClickGnbListener {
            override fun onClickScroll() {
                //do stuff
                this@MainViews.onClickScroll()
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
         * case 3. when you have somthing to do it in Views then call usecase
         */
        rootView.gnb?.onClickGnbListener = object : OnClickGnbListener {
            override fun onClickScroll() {
                //do stuff
                viewsAction.onClickScroll()
            }

            override fun onClickPalette() {
                //do stuff
                viewsAction.onClickPalette()
            }

            override fun onClickSearch() {
                //do stuff
                viewsAction.onClickSearch()
            }

            override fun onClickStatistic() {
                //do stuff
                viewsAction.onClickStatistic()
            }
        }
    }

    private fun onClickScroll() {
        viewsAction.onClickScroll()
    }

    private fun onClickStatistic() {
        viewsAction.onClickStatistic()
    }

    private fun onClickSearch() {
        viewsAction.onClickSearch()
    }

    private fun onClickPalette() {
        viewsAction.onClickPalette()
    }

}