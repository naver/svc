/*
 * Copyright 2018 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.naver.android.svc.core.screen

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * @author bs.nam@navercorp.com
 */
interface DialogPlug {
    val fragmentManagerForDialog: FragmentManager?


    fun showDialog(dialogFragment: DialogFragment) {
        val supportFragmentManager = fragmentManagerForDialog ?: return
        dialogFragment.show(supportFragmentManager, dialogFragment.javaClass.simpleName)
    }

    fun showDialog(dialogFragment: DialogFragment, sharedElement: View, elementId: String) {
        val supportFragmentManager = fragmentManagerForDialog ?: return
        val transaction = supportFragmentManager.beginTransaction()
        transaction.addSharedElement(sharedElement, elementId)
        dialogFragment.show(transaction, dialogFragment.javaClass.simpleName)
    }
}