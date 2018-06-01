package com.naver.android.svc.core.screen

import android.support.v4.app.FragmentActivity

/**
 * @author bs.nam@navercorp.com 2018. 2. 21..
 */
interface SvcScreen<out V, out C> {
    val views: V
    val ct: C

    /**
     * every screen can access to their host Activity
     * because fragment's getActivity() method is final method
     * I had to change name "activity" as "hostActivity"
     */
    val hostActivity: FragmentActivity?

    fun createViews(): V
    fun createControlTower(): C
}