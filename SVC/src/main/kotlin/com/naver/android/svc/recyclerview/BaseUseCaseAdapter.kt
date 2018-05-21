package com.naver.android.svc.recyclerview

import android.support.v7.widget.RecyclerView

import com.naver.android.svc.core.views.UseCase

/**
 * @author bs.nam@navercorp.com 2017. 8. 16..
 */

abstract class BaseUseCaseAdapter<VH : RecyclerView.ViewHolder, U : UseCase>(val useCase: U) : RecyclerView.Adapter<VH>()
