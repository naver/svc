package com.naver.android.svc.core

import android.app.Activity
import android.support.v4.app.FragmentActivity

/**
 * @author bs.nam@navercorp.com 2018. 2. 21..
 */
interface ActivityProvider {
    fun getActivity(): FragmentActivity?
}