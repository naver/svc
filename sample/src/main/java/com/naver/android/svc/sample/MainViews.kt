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

import com.naver.android.svc.core.views.UseCaseViews
import kotlinx.android.synthetic.main.activity_main.view.*

class MainViews : UseCaseViews<MainUseCase>() {

    override val layoutResId = R.layout.activity_main

    override fun onCreated() {
        /**
         * case 1. when you want delegate event totally to CT
         */
        rootView.gnb.onClickGnbListener = useCase

        /**
         * case 2. when you don't want to delegate to CT for those events
         * in this case you don't need to define MainUseCase methods
         */
        rootView.gnb.onClickGnbListener = object : OnClickGnbListener {
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
        rootView.gnb.onClickGnbListener = object : OnClickGnbListener {
            override fun onClickScroll() {
                //do stuff
                useCase.onClickScroll()
            }

            override fun onClickPalette() {
                //do stuff
                useCase.onClickPalette()
            }

            override fun onClickSearch() {
                //do stuff
                useCase.onClickSearch()
            }

            override fun onClickStatistic() {
                //do stuff
                useCase.onClickStatistic()
            }
        }
    }

    private fun onClickScroll() {
        useCase.onClickScroll()
    }

    private fun onClickStatistic() {
        useCase.onClickStatistic()
    }

    private fun onClickSearch() {
        useCase.onClickSearch()
    }

    private fun onClickPalette() {
        useCase.onClickPalette()
    }

}