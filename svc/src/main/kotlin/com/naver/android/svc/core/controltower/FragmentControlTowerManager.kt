package com.naver.android.svc.core.controltower

import android.util.Log
import com.naver.android.svc.core.screen.SvcFragment
import com.naver.android.svc.core.views.Views
import java.lang.reflect.InvocationTargetException
import java.util.HashMap
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/**
 * Developed by skydoves on 2018-12-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("unused", "UNCHECKED_CAST")
class FragmentControlTowerManager {
    private val controlTowers = HashMap<String, ControlTower>()

    fun <T : ControlTower> fetch(fragment: SvcFragment<*>,
                                 controlTowerClass: KClass<*>,
                                 views: Views): T {
        val id = fetchId(fragment.javaClass)
        var fragmentControlTower: ControlTower? = this.controlTowers[id]

        if (controlTowerClass !is ControlTower) {
            RuntimeException("controlTower class(${controlTowerClass.simpleName}) should extends ControlTower class.")
        }

        if (fragmentControlTower == null) {
            fragmentControlTower = create(fragment, controlTowerClass, views, id!!)
        }

        return fragmentControlTower as T
    }

    private fun create(fragment: SvcFragment<*>, ControlTowerClass: KClass<*>, views: Views,
                                          id: String): ControlTower {

        val fragmentControlTower: ControlTower

        try {
            fragmentControlTower = ControlTowerClass.createInstance() as ControlTower
            Log.e("Test", "${fetchId(fragment.javaClass)} created")
        } catch (exception: ClassCastException) {
            throw RuntimeException(exception)
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

        val instance = FragmentControlTowerManager()
    }
}