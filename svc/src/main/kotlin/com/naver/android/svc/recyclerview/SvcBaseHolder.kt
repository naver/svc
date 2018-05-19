package com.naver.android.svc.recyclerview

import android.view.View
import com.naver.android.svc.core.views.UseCase

/**
 * @author bs.nam@navercorp.com 2018. 3. 18..
 */
abstract class SvcBaseHolder<T, U : UseCase>(view: View, useCase: U) : BaseUseCaseHolder<T, U>(view,useCase) {

}