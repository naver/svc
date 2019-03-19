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
package com.naver.android.svc.preference

import android.content.Context
import android.content.SharedPreferences

/**
 * @author bs.nam@navercorp.com 2019. 3. 20..
 */
abstract class AbsPreference
/**
 * @param context
 * @param preferenceName
 */
(context: Context, private val preferenceName: String = "") {

    private val editor: SharedPreferences.Editor
    private val preferences: SharedPreferences


    init {
        preferences = context.getSharedPreferences(getDefaultPreferenceName(context, preferenceName), Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    open fun getDefaultPreferenceName(context: Context, preferenceName: String): String {
        return context.packageName + "." + preferenceName
    }

    fun setPreference(key: String, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }

    fun setPreference(key: String, value: Float) {
        editor.putFloat(key, value)
        editor.commit()
    }

    fun setPreference(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }

    fun setPreference(key: String, value: Long) {
        editor.putLong(key, value)
        editor.commit()
    }

    fun setPreference(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getPreferenceBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun getPreferenceBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun getPreferenceFloat(key: String): Float {
        return preferences.getFloat(key, 0.0f)
    }

    @JvmOverloads
    fun getPreferenceInt(key: String, defaultValue: Int = 0): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun getPreferenceLong(key: String): Long {
        return preferences.getLong(key, 0L)
    }

    @JvmOverloads
    fun getPreferenceString(key: String, defaultValue: String? = null): String? {
        return preferences.getString(key, defaultValue)
    }
}
