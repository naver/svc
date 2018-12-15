package com.naver.android.svc.core.controltower

import android.os.Bundle
import com.naver.android.svc.core.screen.SvcActivity
import com.naver.android.svc.core.utils.BundleUtils
import com.naver.android.svc.core.views.Views
import java.lang.reflect.InvocationTargetException
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/**
 * Developed by skydoves on 2018-12-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("unused")
class ActivityControlTowerManager {
    private val controlTowers = HashMap<String, ControlTower>()

    fun <T : ControlTower> fetch(activity: SvcActivity<*>,
                                 controlTowerClass: KClass<T>,
                                 views: Views,
                                 savedInstanceState: Bundle?): T {
        val id = fetchId(savedInstanceState)
        var activityControlTower: ControlTower? = this.controlTowers[id]

        if (activityControlTower == null) {
            activityControlTower = create(activity, controlTowerClass, views, savedInstanceState, id!!)
        }

        return activityControlTower as T
    }

    fun save(activityControlTower: ControlTower, envelope: Bundle) {
        envelope.putString(ControlTower_ID_KEY, findIdForControlTower(activityControlTower))
        val state = Bundle()
        envelope.putBundle(ControlTower_STATE_KEY, state)
    }

    private fun <T : ControlTower> create(activity: SvcActivity<*>, ControlTowerClass: KClass<T>, views: Views,
                                          savedInstanceState: Bundle?, id: String): ControlTower {

        val activityControlTower: ControlTower

        try {
            activityControlTower = ControlTowerClass.createInstance()
        } catch (exception: IllegalAccessException) {
            throw RuntimeException(exception)
        } catch (exception: InvocationTargetException) {
            throw RuntimeException(exception)
        } catch (exception: InstantiationException) {
            throw RuntimeException(exception)
        } catch (exception: NoSuchMethodException) {
            throw RuntimeException(exception)
        }

        this.controlTowers[id] = activityControlTower
        activityControlTower.onCreateControlTower(activity, views, BundleUtils.maybeGetBundle(savedInstanceState, ControlTower_STATE_KEY))
        return activityControlTower
    }

    fun destroy(activityControlTower: ControlTower) {
        activityControlTower.onDestroy()

        val iterator = this.controlTowers.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (activityControlTower == entry.value) {
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

    private fun findIdForControlTower(activityControlTower: ControlTower): String {
        for ((key, value) in this.controlTowers) {
            if (activityControlTower == value) {
                return key
            }
        }

        throw RuntimeException("cannot find ControlTower in map!")
    }

    companion object {
        private val ControlTower_ID_KEY = "ControlTower_id"
        private val ControlTower_STATE_KEY = "ControlTower_state"

        val instance = ActivityControlTowerManager()
    }
}