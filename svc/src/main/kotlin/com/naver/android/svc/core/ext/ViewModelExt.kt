package com.naver.android.svc.core.ext

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.naver.android.svc.core.screen.Screen
import java.lang.IllegalStateException

/**
 * helper methods for Screen
 * @author bs.nam@navercorp.com
 */

@NonNull
@MainThread
fun ViewModelProviders.of(screen: Screen<*, *>): ViewModelProvider {
    return when (screen) {
        is Fragment -> ViewModelProviders.of(screen)
        is FragmentActivity -> ViewModelProviders.of(screen)
        else -> throw IllegalStateException("screen should be Fragment or FragmentActivity")
    }
}