package com.naver.android.svc.core.controltower

import android.os.Bundle
import com.naver.android.svc.core.screen.SvcDialogFragment
import com.naver.android.svc.core.utils.BundleUtils
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
                                 views: Views,
                                 savedInstanceState: Bundle?): T {
        val id = fetchId(savedInstanceState)
        var fragmentControlTower: ControlTower? = this.controlTowers[id]

        if (fragmentControlTower == null) {
            fragmentControlTower = create(fragment, controlTowerClass, views, savedInstanceState, id!!)
        }

        return fragmentControlTower as T
    }

    fun save(fragmentControlTower: ControlTower, envelope: Bundle) {
        envelope.putString(ControlTower_ID_KEY, findIdForControlTower(fragmentControlTower))
        val state = Bundle()
        envelope.putBundle(ControlTower_STATE_KEY, state)
    }

    private fun <T : ControlTower> create(fragment: SvcDialogFragment<*, *>, ControlTowerClass: KClass<T>, views: Views,
                                          savedInstanceState: Bundle?, id: String): ControlTower {

        val fragmentControlTower: ControlTower

        try {
            fragmentControlTower = ControlTowerClass.createInstance()
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
        fragmentControlTower.onCreateControlTower(fragment, views, BundleUtils.maybeGetBundle(savedInstanceState, ControlTower_STATE_KEY))
        return fragmentControlTower
    }

    fun destroy(fragmentControlTower: ControlTower) {
        fragmentControlTower.onDestroy()

        val iterator = this.controlTowers.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (fragmentControlTower == entry.value) {
                iterator.remove()
            }
        }
    }

    private fun fetchId(savedInstanceState: Bundle?): String? {
        return if (savedInstanceState != null)
            savedInstanceState.getString(ControlTower_STATE_KEY)
        else
            UUID.randomUUID().toString()
    }

    private fun findIdForControlTower(fragmentControlTower: ControlTower): String {
        for ((key, value) in this.controlTowers) {
            if (fragmentControlTower == value) {
                return key
            }
        }

        throw RuntimeException("cannot find ControlTower in map!")
    }

    companion object {
        private const val ControlTower_ID_KEY = "ControlTower_id"
        private const val ControlTower_STATE_KEY = "ControlTower_state"

        val instance = DialogFragmentControlTowerManager()
    }
}