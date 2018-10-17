package com.naver.android.svc.core.ext

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.naver.android.svc.core.screen.Screen

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