package com.naver.android.svc.recyclerview

import android.view.View

import com.naver.android.svc.core.views.UseCase

/**
 * @author bs.nam@navercorp.com 2017. 8. 16..
 */

abstract class BaseUseCaseHolder<T, U : UseCase>(itemView: View, val useCase: U) : BaseBindHolder<T>(itemView)
