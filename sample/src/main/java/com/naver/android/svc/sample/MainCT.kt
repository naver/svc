package com.naver.android.svc.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import com.naver.android.svc.core.SvcBaseCT
import com.naver.android.svc.core.SvcBaseFragment
import com.naver.android.svc.sample.core.MainViews
import com.naver.android.svc.sample.core.tabs.palette.PaletteFragment
import com.naver.android.svc.sample.tabs.MainTab
import com.naver.android.svc.sample.tabs.scroll.ScrollFragment
import com.naver.android.svc.sample.tabs.search.SearchFragment
import com.naver.android.svc.sample.tabs.statistic.StatisticFragment

class MainCT(owner: MainActivity, views: MainViews) : SvcBaseCT<MainActivity, MainViews>(owner, views), MainUseCase {
    val fragmentMap = mapOf<MainTab, Fragment>(
            MainTab.SCROLL to ScrollFragment(),
            MainTab.PALETTE to PaletteFragment(),
            MainTab.SEARCH to SearchFragment(),
            MainTab.STATISTIC to StatisticFragment())

    var instanceNo = 0

    override fun onClickScroll() {
        replaceFragment(MainTab.SCROLL)
    }

    override fun onClickPalette() {
        replaceFragment(MainTab.PALETTE)
    }

    override fun onClickSearch() {
        replaceFragment(MainTab.SEARCH)
    }

    override fun onClickStatistic() {
        replaceFragment(MainTab.STATISTIC)
    }

    override fun onCreated() {
    }

    private fun replaceFragment(tab: MainTab) {
        val fragment = fragmentMap[tab]
        fragment ?: return

        val bundle = Bundle()
        bundle.putString(SvcBaseFragment.EXTRA_TAG_ID, tab.name)
        fragment.arguments = bundle

        val transaction = owner.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(tab.name)
        transaction.commit()
    }
}