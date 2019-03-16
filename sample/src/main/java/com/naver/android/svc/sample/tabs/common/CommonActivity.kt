package com.naver.android.svc.sample.tabs.common

import android.content.Intent
import com.naver.android.annotation.RequireControlTower
import com.naver.android.annotation.RequireViews
import com.naver.android.annotation.SvcActivity

/**
 * @author bs.nam@navercorp.com
 */
@SvcActivity
@RequireControlTower(CommonControlTower::class)
@RequireViews(CommonViews::class)
class CommonActivity : SVC_CommonActivity(), CommonScreen {

  override fun startCommonActivity() {
    val intent = Intent(this, CommonActivity::class.java)
    startActivity(intent)
  }
}