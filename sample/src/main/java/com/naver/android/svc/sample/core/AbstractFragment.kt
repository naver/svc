package com.naver.android.svc.sample.core

import com.naver.android.svc.core.SvcBaseCT
import com.naver.android.svc.core.SvcBaseFragment
import com.naver.android.svc.core.views.SvcBaseViews

abstract class AbstractFragment<out V : SvcBaseViews<*>, out C : SvcBaseCT<*, *>>: SvcBaseFragment<V, C>() {
}