package com.naver.android.svc.sample

import com.naver.android.svc.core.views.UseCaseViews
import kotlinx.android.synthetic.main.activity_main.*

class MainViews(screen: MainActivity) : UseCaseViews<MainActivity, MainUseCase>(screen) {

    override val layoutResId = R.layout.activity_main

    override fun onCreated() {
        /**
         * case 1. when you want delegate event totally to CT
         */
        screen.gnb.onClickGnbListener = usecase

        /**
         * case 2. when you don't want to delegate to CT for those events
         * in this case you don't need to define MainUseCase methods
         */
        screen.gnb.onClickGnbListener = object : OnClickGnbListener {
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
         * case 3. when you have somthing to do it in Views
         */
        screen.gnb.onClickGnbListener = object : OnClickGnbListener {
            override fun onClickScroll() {
                //do stuff
                usecase.onClickScroll()
            }

            override fun onClickPalette() {
                //do stuff
                usecase.onClickPalette()
            }

            override fun onClickSearch() {
                //do stuff
                usecase.onClickSearch()
            }

            override fun onClickStatistic() {
                //do stuff
                usecase.onClickStatistic()
                //do stuff
            }
        }
    }

    private fun onClickScroll() {
    }

    private fun onClickStatistic() {
    }

    private fun onClickSearch() {
    }

    private fun onClickPalette() {
    }

}