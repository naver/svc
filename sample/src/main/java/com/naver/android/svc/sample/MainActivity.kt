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

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.naver.android.annotation.svc.RequireControlTower
import com.naver.android.annotation.svc.RequireViews
import com.naver.android.annotation.svc.SvcActivity
import com.naver.android.svc.core.screen.SvcFragment
import com.naver.android.svc.sample.tabs.MainTab
import com.naver.android.svc.sample.tabs.home.HomeFragment
import com.naver.android.svc.sample.tabs.palette.PaletteFragment
import com.naver.android.svc.sample.tabs.paper.PaperFragment
import com.naver.android.svc.sample.tabs.search.ReallyLongScreenNameSearchFragment
import com.naver.android.svc.sample.tabs.statistic.StatisticFragment

@SvcActivity
@RequireViews(MainViews::class)
@RequireControlTower(MainControlTower::class)
class MainActivity : SVC_MainActivity() {
    private val fragmentMap = mapOf<MainTab, Fragment>(
        MainTab.HOME to HomeFragment(),
        MainTab.PAPER to PaperFragment(),
        MainTab.PALETTE to PaletteFragment(),
        MainTab.SEARCH to ReallyLongScreenNameSearchFragment(),
        MainTab.STATISTIC to StatisticFragment())

    fun changeScreen(tab: MainTab) {
        val fragment = fragmentMap[tab]
        fragment ?: return

        val bundle = Bundle()
        bundle.putString(SvcFragment.EXTRA_TAG_ID, tab.name)
        fragment.arguments = bundle

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(tab.name)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.fragment_container) is HomeFragment) {
            finish()
        } else {
            changeScreen(MainTab.HOME)
        }
    }
}
