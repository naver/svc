/*
 * Copyright 2018 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        fun of(screen: Screen<*>): ViewModelProvider {
            return when (screen) {
                is Fragment -> ViewModelProviders.of(screen)
                is FragmentActivity -> ViewModelProviders.of(screen)
                else -> throw IllegalStateException("screen should be Fragment or FragmentActivity")
            }
        }
    }
}
