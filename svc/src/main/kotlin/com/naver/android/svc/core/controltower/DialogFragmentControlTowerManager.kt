package com.naver.android.svc.core.controltower

import android.util.Log
import com.naver.android.svc.core.screen.SvcDialogFragment
import com.naver.android.svc.core.views.Views
import java.lang.reflect.InvocationTargetException
import java.util.HashMap
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/**
 * Developed by skydoves on 2018-12-16.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("unused", "UNCHECKED_CAST")
class DialogFragmentControlTowerManager {
    private val controlTowers = HashMap<String, ControlTower>()

    fun <T : ControlTower> fetch(fragment: SvcDialogFragment<*, *>,
                                 controlTowerClass: KClass<T>,
                                 views: Views): T {
        val id = fetchId(fragment.javaClass)
        var fragmentControlTower: ControlTower? = this.controlTowers[id]

        if (fragmentControlTower == null) {
            fragmentControlTower = create(fragment, controlTowerClass, views, id!!)
        }

        return fragmentControlTower as T
    }

    private fun <T : ControlTower> create(fragment: SvcDialogFragment<*, *>, ControlTowerClass: KClass<T>, views: Views,
                                          id: String): ControlTower {

        val fragmentControlTower: ControlTower

        try {
            fragmentControlTower = ControlTowerClass.createInstance()
            Log.e("Test", "${fetchId(fragment.javaClass)} created")
        } catch (exception: IllegalAccessException) {
            throw RuntimeException(exception)
        } catch (exception: InvocationTargetException) {
            throw RuntimeException(exception)
        } catch (exception: InstantiationException) {
            throw RuntimeException(exception)
        } catch (exception: NoSuchMethodException) {
            throw RuntimeException(exception)
        }

        this.controlTowers[id] = fragmentControlTower
        fragmentControlTower.onCreateControlTower(fragment, views)
        return fragmentControlTower
    }

    fun destroy(fragmentControlTower: ControlTower) {
        fragmentControlTower.onDestroy()

        val iterator = this.controlTowers.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (fragmentControlTower == entry.value) {
                iterator.remove()
                Log.e("Test", "${entry.key} destroyed")
            }
        }
    }

    private fun fetchId(modelClass: Class<*>): String? {
        return "$ControlTower_ID_KEY:${getCanonicalName(modelClass)}"
    }

    private fun findIdForControlTower(fragmentControlTower: ControlTower): String {
        for ((key, value) in this.controlTowers) {
            if (fragmentControlTower == value) {
                return key
            }
        }

        throw RuntimeException("cannot find ControlTower in map!")
    }

    private fun getCanonicalName(clazz: Class<*>): String {
        return clazz.canonicalName ?: UUID.randomUUID().toString()
    }

    companion object {
        private const val ControlTower_ID_KEY = "ControlTower_id"

        val instance = DialogFragmentControlTowerManager()
    }
}