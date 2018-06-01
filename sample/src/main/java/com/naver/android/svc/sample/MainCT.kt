package com.naver.android.svc.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import com.naver.android.svc.core.SvcCT
import com.naver.android.svc.core.screen.SvcFragment
import com.naver.android.svc.sample.tabs.MainTab
import com.naver.android.svc.sample.tabs.palette.PaletteFragment
import com.naver.android.svc.sample.tabs.scroll.ScrollFragment
import com.naver.android.svc.sample.tabs.search.SearchFragment
import com.naver.android.svc.sample.tabs.statistic.StatisticFragment

class MainCT(screen: MainActivity, views: MainViews) : SvcCT<MainActivity, MainViews>(screen, views), MainUseCase {
    private val fragmentMap = mapOf<MainTab, Fragment>(
            MainTab.SCROLL to ScrollFragment(),
            MainTab.PALETTE to PaletteFragment(),
            MainTab.SEARCH to SearchFragment(),
            MainTab.STATISTIC to StatisticFragment())

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
        bundle.putString(SvcFragment.EXTRA_TAG_ID, tab.name)
        fragment.arguments = bundle

        val transaction = screen.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(tab.name)
        transaction.commit()
    }
}