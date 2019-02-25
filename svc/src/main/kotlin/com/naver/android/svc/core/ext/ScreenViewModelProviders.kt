package com.naver.android.svc.core.ext

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.naver.android.svc.core.screen.Screen

/**
 * provide Proviers for Screen
 * because cannot extend static function for class used independent class (https://youtrack.jetbrains.com/issue/KT-11968)
 * @author bs.nam@navercorp.com
 */
class ScreenViewModelProviers {
    companion object {
        fun of(screen: Screen<*>): ViewModelProvider {
            return when (screen) {
                is Fragment -> ViewModelProviders.of(screen)
                is FragmentActivity -> ViewModelProviders.of(screen)
                else -> throw IllegalStateException("screen should be Fragment or FragmentActivity")
            }
        }
    }
}