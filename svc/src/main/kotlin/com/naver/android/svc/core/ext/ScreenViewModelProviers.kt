package com.naver.android.svc.core.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.naver.android.svc.core.screen.Screen

/**
 * provide Proviers for Screen
 * because cannot extend static function for class used independent class (https://youtrack.jetbrains.com/issue/KT-11968)
 * @author bs.nam@navercorp.com
 */
class ScreenViewModelProviers {
    companion object {
        fun of(screen: Screen<*, *>): ViewModelProvider {
            return when (screen) {
                is Fragment -> ViewModelProviders.of(screen)
                is FragmentActivity -> ViewModelProviders.of(screen)
                else -> throw IllegalStateException("screen should be Fragment or FragmentActivity")
            }
        }
    }
}