package com.naver.android.svc.sample.tabs.home

import com.naver.android.annotation.OwnSvcFragment
import com.naver.android.annotation.RequireControlTower
import com.naver.android.annotation.RequireViews
import com.naver.android.svc.sample.tabs.common.CommonViews

/**
 * @author bs.nam@navercorp.com
 */
@OwnSvcFragment
@RequireViews(CommonViews::class)
@RequireControlTower(HomeControlTower::class)
class HomeFragment : SVC_HomeFragment()