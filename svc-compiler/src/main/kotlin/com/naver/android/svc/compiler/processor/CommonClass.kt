package com.naver.android.svc.compiler.processor

import com.squareup.kotlinpoet.ClassName

/**
 * @author bs.nam@navercorp.com
 */
object CommonClass{
    val Views = ClassName("com.naver.android.svc.core.views", "Views")
    val Screen = ClassName("com.naver.android.svc.core.screen", "Screen")
    val ControlTower = ClassName("com.naver.android.svc.core.controltower", "ControlTower")
}